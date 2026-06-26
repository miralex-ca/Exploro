package com.exploramus.data.localdb

import app.cash.sqldelight.db.SqlDriver
import appLocalDb.AppLocalDb
import com.exploramus.core.models.Country
import com.exploramus.core.models.CountryDetails
import com.exploramus.core.models.CountryWithDetails
import com.exploramus.data.common.LocalDataSource

internal object DatabaseConfig {
    const val NAME = "applocal.db"
    const val VERSION = 5L
}

internal fun createLocalDataSource(sqlDriver: SqlDriver): LocalDataSource {
    val appLocalDb = AppLocalDb(sqlDriver)
    return SQLDelightLocalDataSource(
        appLocalDb,
        DatabaseManager.Base(sqlDriver, appLocalDb)
    )
}

internal class SQLDelightLocalDataSource(
    private val database:  AppLocalDb,
    private val dbManager: DatabaseManager,
) : LocalDataSource {

    override val databaseVersion: Long
        get() = DatabaseConfig.VERSION

    private val favoritesQueries = database.favoritesQueries

    override suspend fun setCountriesList(list: List<Country>) {
        database.setCountriesList(list)
    }

    override suspend fun setCountryDetailsList(list: List<CountryDetails>) {
        database.setCountriesDetailsList(list)
    }

    override suspend fun getAllCountriesBySectionId(sectionId: String): List<Country> {
        return database.getAllCountriesBySection(sectionId).toCountryList()
    }

    override suspend fun searchCountries(query: String): List<Country> {
        return database.searchCountries(query).toCountryList()
    }

    override suspend fun getAllCountriesWithDetails(): List<CountryWithDetails> {
        return database.getAllCountriesWithDetails().map { it.toCountryWithDetails() }
    }

    override suspend fun hasCountriesData(): Boolean {
        return database.getCountriesCount() > 0
    }

    override suspend fun getCountriesBySection(sectionId: String, limit: Long) : List<Country> {
        return database.getCountriesByContinent(sectionId, limit).toCountryList()
    }

    override suspend fun isFavorite(id: String): Boolean {
        return favoritesQueries
            .isFavorite(id)
            .executeAsOne()
    }

    override suspend fun addFavorite(id: String) {
        favoritesQueries.addFavorite(id)
    }

    override suspend fun removeFavorite(id: String) {
        favoritesQueries.removeFavorite(id)
    }

    override suspend fun getFavorites(): List<Country> {
        return database.getFavorites().toCountryList()
    }

    override suspend fun resetAndMigrate() {
        dbManager.rebuildDatabase()
    }

    override suspend fun getCountryDetailsById(id: String): CountryWithDetails? {
        return database.getCountryDetailsById(id)?.toCountryWithDetails()
    }
}
