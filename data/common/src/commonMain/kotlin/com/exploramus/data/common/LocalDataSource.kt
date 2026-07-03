package com.exploramus.data.common

import com.exploramus.core.models.Country
import com.exploramus.core.models.CountryDetails
import com.exploramus.core.models.CountryWithDetails
import com.exploramus.core.models.Section

interface LocalDataSource {
    val databaseVersion: Long
    suspend fun setCountriesList(list: List<Country>)
    suspend fun isFavorite(id: String): Boolean
    suspend fun addFavorite(id: String)
    suspend fun removeFavorite(id: String)
    suspend fun getFavorites(): List<Country>
    suspend fun getFavoritesWithDetails(): List<CountryWithDetails>
    suspend fun resetAndMigrate()
    suspend fun getCountryDetailsById(id: String):  CountryWithDetails?
    suspend fun setCountryDetailsList(list: List<CountryDetails>)
    suspend fun getCountriesBySection(sectionId: String, limit: Long = 12): List<Country>
    suspend fun getAllCountriesBySectionId(sectionId: String): List<Country>
    suspend fun getAllCountriesWithDetailsBySectionId(sectionId: String): List<CountryWithDetails>
    suspend fun searchCountries(query: String): List<Country>
    suspend fun getAllCountriesWithDetails(): List<CountryWithDetails>
    suspend fun hasCountriesData(): Boolean
    suspend fun setSections(sections: List<Section>)
    suspend fun getSections(): List<Section>
    suspend fun getFavoritesCount(): Long
    suspend fun getAllCountriesCount(): Long
    suspend fun getCountriesCountBySection(sectionId: String): Long
}