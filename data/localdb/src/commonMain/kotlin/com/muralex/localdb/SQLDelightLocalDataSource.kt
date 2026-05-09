package com.muralex.localdb

import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import appLocalDb.AppLocalDb
import appLocalDb.Countries
import com.muralex.data.common.LocalDataSource
import com.muralex.models.Country
import com.muralex.models.CountryUserData
import org.koin.core.module.Module
import org.koin.dsl.module


//expect val localdbModule: Module


class SQLDelightLocalDataSource(
    sqlDriver: SqlDriver
) : LocalDataSource {

    private val database = AppLocalDb(
        sqlDriver,
        Countries.Adapter(IntColumnAdapter)
    )

    private val favoritesQueries = database.favoritesQueries

    override suspend fun getCountriesList(): List<Country> = database.getCountriesList()

    override suspend fun getCountriesWithUserData(): List<CountryUserData> =
        database.getCountriesWithUserData()

    override suspend fun setCountriesList(list: List<Country>) {
        database.setCountriesList(list)
    }

    override suspend fun addFavorite(id: String) =
        favoritesQueries.addFavorite(id)

    override suspend fun removeFavorite(id: String) =
        favoritesQueries.removeFavorite(id)

    override suspend fun getFavoriteCountriesMap(): Map<String, Boolean> =
        favoritesQueries.getFavorites().executeAsList().associate { it.id to true }


}