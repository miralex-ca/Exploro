package com.muralex.data.common

import com.muralex.models.Country
import com.muralex.models.CountryDetails
import com.muralex.models.CountryWithDetails

interface LocalDataSource {
    val databaseVersion: Long
    suspend fun setCountriesList(list: List<Country>)
    suspend fun isFavorite(id: String): Boolean
    suspend fun addFavorite(id: String)
    suspend fun removeFavorite(id: String)
    suspend fun getFavorites(): List<Country>
    suspend fun resetAndMigrate()
    suspend fun getCountryDetailsById(id: String):  CountryWithDetails?
    suspend fun setCountryDetailsList(list: List<CountryDetails>)
    suspend fun getCountriesBySection(sectionId: String, limit: Long = 12): List<Country>
    suspend fun getAllCountriesBySectionId(sectionId: String): List<Country>
    suspend fun searchCountries(query: String): List<Country>
    suspend fun hasCountriesData(): Boolean
}