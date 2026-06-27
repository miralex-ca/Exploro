package com.exploramus.data.localdb

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import appLocalDb.AppLocalDb
import com.exploramus.core.models.Country
import com.exploramus.core.models.CountryDetails
import com.exploramus.data.common.LocalDataSource
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LocalDataSourceTest {

    private fun createTestDataSource(): LocalDataSource {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        AppLocalDb.Schema.create(driver)
        return createLocalDataSource(driver)
    }


    @Test
    fun `database starts empty`() = runTest {
        val source = createTestDataSource()
        assertFalse(source.hasCountriesData())
    }

    @Test
    fun `setCountriesList stores countries`() = runTest {
        val source = createTestDataSource()
        source.setCountriesList(listOf(testCountry))
        assertTrue(source.hasCountriesData())
    }

    @Test
    fun `setCountriesList upserts existing country`() = runTest {
        val source = createTestDataSource()
        source.setCountriesList(listOf(testCountry))
        val updated = testCountry.copy(name = "France Updated")
        source.setCountriesList(listOf(updated))
        assertTrue(source.hasCountriesData())
    }

    @Test
    fun `getAllCountriesBySectionId returns countries for continent`() = runTest {
        val source = createTestDataSource()
        source.setCountriesList(listOf(testCountry))
        val result = source.getAllCountriesBySectionId("Europe")
        assertEquals(1, result.size)
        assertEquals("FRA", result.first().id)
    }

    @Test
    fun `getAllCountriesBySectionId returns empty for unknown continent`() = runTest {
        val source = createTestDataSource()
        source.setCountriesList(listOf(testCountry))
        val result = source.getAllCountriesBySectionId("Antarctica")
        assertTrue(result.isEmpty())
    }

    @Test
    fun `searchCountries finds by name`() = runTest {
        val source = createTestDataSource()
        source.setCountriesList(listOf(testCountry))
        val result = source.searchCountries("France")
        assertEquals(1, result.size)
    }

    @Test
    fun `searchCountries returns empty for no match`() = runTest {
        val source = createTestDataSource()
        source.setCountriesList(listOf(testCountry))
        val result = source.searchCountries("xyz")
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getAllCountriesWithDetails returns all entries`() = runTest {
        val source = createTestDataSource()
        source.setCountriesList(listOf(testCountry))
        source.setCountryDetailsList(listOf(testDetails))
        val result = source.getAllCountriesWithDetails()
        assertEquals(1, result.size)
    }

    @Test
    fun `addFavorite makes country favorite`() = runTest {
        val source = createTestDataSource()
        source.setCountriesList(listOf(testCountry))
        source.addFavorite("FRA")
        assertTrue(source.isFavorite("FRA"))
    }

    @Test
    fun `removeFavorite removes country from favorites`() = runTest {
        val source = createTestDataSource()
        source.setCountriesList(listOf(testCountry))
        source.addFavorite("FRA")
        source.removeFavorite("FRA")
        assertFalse(source.isFavorite("FRA"))
    }

    @Test
    fun `getFavorites returns added favorites`() = runTest {
        val source = createTestDataSource()
        source.setCountriesList(listOf(testCountry))
        source.addFavorite("FRA")
        val favorites = source.getFavorites()
        assertEquals(1, favorites.size)
        assertEquals("FRA", favorites.first().id)
    }

    private val testCountry = Country(
        id = "FRA",
        iso2 = "FR",
        name = "France",
        officialName = "French Republic",
        capital = "Paris",
        continent = "Europe",
        location = "Western Europe",
        flagImage = "https://flag.png",
        flagEmoji = "🇫🇷"
    )

    private val testDetails = CountryDetails(
        id = "FRA",
        population = 69081996,
        totalArea = 551695.0,
        coatOfArmsUrl = "https://coat.png",
        currencyName = "Euro",
        currencySymbol = "€",
        currencyCode = "EUR",
        languages = listOf("French"),
        latitude = 48.87,
        longitude = 2.33,
        timezones = listOf("UTC+01:00"),
        mapsUrl = "https://maps.google.com",
        wikiUrl = "https://en.wikipedia.org/wiki/France"
    )

}
