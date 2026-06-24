package com.exploramus.data.localdb

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import appLocalDb.AppLocalDb
import com.exploramus.core.models.Country
import com.exploramus.data.common.LocalDataSource
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DatabaseManagerTest {

    private fun createTestSetup(): Pair<LocalDataSource, DatabaseManager> {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        AppLocalDb.Schema.create(driver)
        val db = AppLocalDb(driver)
        val manager = DatabaseManager.Base(driver, db)
        val source = SQLDelightLocalDataSource(db, manager)
        return Pair(source, manager)
    }

    private val testCountry = Country(
        id = "FRA",
        name = "France",
        officialName = "French Republic",
        capital = "Paris",
        continent = "Europe",
        subregion = "Western Europe",
        flagPngUrl = "",
        flagAlt = ""
    )

    @Test
    fun `rebuild clears countries`() = runTest {
        val (source, manager) = createTestSetup()
        source.setCountriesList(listOf(testCountry))
        assertTrue(source.hasCountriesData())
        manager.rebuildDatabase()
        assertFalse(source.hasCountriesData())
    }

    @Test
    fun `rebuild preserves favorites`() = runTest {
        val (source, manager) = createTestSetup()
        source.setCountriesList(listOf(testCountry))
        source.addFavorite("FRA")
        manager.rebuildDatabase()
        source.setCountriesList(listOf(testCountry))
        assertTrue(source.isFavorite("FRA"))
    }

    @Test
    fun `rebuild allows new data to be inserted`() = runTest {
        val (source, manager) = createTestSetup()
        manager.rebuildDatabase()
        source.setCountriesList(listOf(testCountry))
        assertTrue(source.hasCountriesData())
    }
}