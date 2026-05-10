package com.muralex.localdb

import app.cash.sqldelight.db.SqlDriver
import appLocalDb.AppLocalDb
import com.muralex.data.common.LocalDataSource
import com.muralex.models.Country
import com.muralex.models.CountryUserData


class SQLDelightLocalDataSource(
    sqlDriver: SqlDriver
) : LocalDataSource {

    private val database = AppLocalDb(
        sqlDriver
    )

    private val favoritesQueries = database.favoritesQueries

    override suspend fun getCountriesList(): List<Country> = database.getCountriesList()

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

}
