package com.exploramus.data.common

import com.exploramus.core.models.*

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
    suspend fun getAllCountries(): List<Country>
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

    suspend fun saveQuizResult(result: QuizResult)
    suspend fun getQuizResult(quizId: String): QuizResult?
    suspend fun getQuizResults(quizIds: List<String>): List<QuizResult>

    suspend fun saveQuizItemResult(result: QuizItemResult)
    suspend fun getQuizItemResult(id: String): QuizItemResult?
    suspend fun getQuizItemResults(ids: List<String>): List<QuizItemResult>
    suspend fun getAllQuizItemResults(): List<QuizItemResult>
}
