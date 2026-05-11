package com.muralex.data.common

import com.muralex.models.Country
import com.muralex.models.CountryDetails
import com.muralex.models.CountryListItem
import com.muralex.models.CountryUserData

interface LocalDataSource {
    val databaseVersion: Long
    suspend fun getCountriesList(): List<CountryListItem>
    suspend fun getCountriesWithUserData(): List<CountryUserData>
    suspend fun setCountriesList(list: List<Country>)
    suspend fun addFavorite(id: String)
    suspend fun removeFavorite(id: String)
    suspend fun getFavoriteCountriesMap(): Map<String, Boolean>
    suspend fun resetAndMigrate()
    suspend fun getCountryDetailsById(id: String): CountryDetails?
}