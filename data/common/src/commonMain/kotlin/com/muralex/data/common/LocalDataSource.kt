package com.muralex.data.common

import com.muralex.models.Country
import com.muralex.models.CountryDetails
import com.muralex.models.CountryFull
import com.muralex.models.CountryListItem
import com.muralex.models.CountryUserData

interface LocalDataSource {
    val databaseVersion: Long
    suspend fun getCountriesList(): List<CountryListItem>
    suspend fun getCountriesWithUserData(): List<CountryUserData>
    suspend fun setCountriesList(list: List<Country>)
    suspend fun isFavorite(id: String): Boolean
    suspend fun addFavorite(id: String)
    suspend fun removeFavorite(id: String)
    suspend fun getFavorites(): List<CountryListItem>
    suspend fun resetAndMigrate()
    suspend fun getCountryDetailsById(id: String):  CountryFull?
    suspend fun setCountryDetailsList(list: List<CountryDetails>)
    suspend fun  getCountriesByContinent(continent: String, limit: Long = 12): List<CountryListItem>
    suspend fun getAllCountriesByContinent(continent: String): List<CountryListItem>
    suspend fun searchCountries(query: String): List<CountryListItem>
}