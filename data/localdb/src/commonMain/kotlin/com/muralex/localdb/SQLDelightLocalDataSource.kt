package com.muralex.localdb

import app.cash.sqldelight.db.SqlDriver
import appLocalDb.AppLocalDb
import com.muralex.data.common.LocalDataSource
import com.muralex.models.Country
import com.muralex.models.CountryDetails
import com.muralex.models.CountryListItem
import com.muralex.models.CountryUserData

internal object DatabaseConfig {
    const val NAME = "applocal.db"
    const val VERSION = 4L
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

    override suspend fun addFavorite(id: String) {
        favoritesQueries.addFavorite(id)
    }

    override suspend fun removeFavorite(id: String) {
        favoritesQueries.removeFavorite(id)
    }

    override suspend fun getFavoriteCountriesMap(): Map<String, Boolean> =
        favoritesQueries.getFavorites().executeAsList().associate { it.id to true }

    override suspend fun resetAndMigrate() {
        dbManager.rebuildDatabase()
    }

    override suspend fun getCountryDetailsById(id: String): CountryDetails? {
        return database.getCountryDetailsById(id)?.toDetails()
    }
}
