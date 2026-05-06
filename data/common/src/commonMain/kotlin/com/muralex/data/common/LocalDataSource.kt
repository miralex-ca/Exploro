package com.muralex.data.common

import com.muralex.models.Country
import com.muralex.models.CountryUserData

interface LocalDataSource {
    suspend fun getCountriesList(): List<Country>
    suspend fun getCountriesWithUserData(): List<CountryUserData>
    suspend fun setCountriesList(list: List<Country>)
    suspend fun addFavorite(id: String)
    suspend fun removeFavorite(id: String)
    suspend fun getFavoriteCountriesMap(): Map<String, Boolean>
}