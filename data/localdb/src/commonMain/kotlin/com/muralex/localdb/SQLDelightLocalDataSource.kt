package com.muralex.localdb

import app.cash.sqldelight.db.SqlDriver
import appLocalDb.AppLocalDb
import com.muralex.data.common.LocalDataSource
import com.muralex.models.Country
import com.muralex.models.CountryDetails
import com.muralex.models.CountryFull
import com.muralex.models.CountryListItem
import com.muralex.models.CountryUserData

internal object DatabaseConfig {
    const val NAME = "applocal.db"
    const val VERSION = 12L
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

    override suspend fun getCountriesList(): List<CountryListItem> {
        return database.getCountriesList().toListItems()
    }

    override suspend fun getCountriesWithUserData(): List<CountryUserData> =
        database.getCountriesWithUserData()

    override suspend fun setCountriesList(list: List<Country>) {
        database.setCountriesList(list)
    }

    override suspend fun setCountryDetailsList(list: List<CountryDetails>) {
        database.setCountriesDetailsList(list)
    }

    override suspend fun getAllCountriesByContinent(continent: String): List<CountryListItem> {
        return database.getAllCountriesByContinent(continent).toListItems()
    }

    override suspend fun getCountriesByContinent(continent: String, limit: Long) : List<CountryListItem> {
        val countries =  database.getCountriesByContinent(continent, limit)
        return countries.toListItems()
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

    override suspend fun getFavorites(): List<CountryListItem> {
        return database.getFavorites().toListItems()
    }

    override suspend fun resetAndMigrate() {
        dbManager.rebuildDatabase()
    }

    override suspend fun getCountryDetailsById(id: String): CountryFull? {
        return database.getCountryDetailsById(id)?.toDomain()
    }
}
