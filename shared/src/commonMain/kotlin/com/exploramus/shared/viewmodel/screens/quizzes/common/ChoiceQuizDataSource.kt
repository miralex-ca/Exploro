package com.exploramus.shared.viewmodel.screens.quizzes.common

import com.exploramus.core.models.Country
import com.exploramus.core.models.QuizItemResult
import com.exploramus.core.models.Section
import com.exploramus.data.repository.Repository
import com.exploramus.data.repository.functions.getFlashcardCountriesAll
import com.exploramus.data.repository.functions.getFlashcardCountriesBySection
import com.exploramus.data.repository.functions.getFlashcardCountriesFavorites
import com.exploramus.data.repository.functions.getQuizItemResults
import com.exploramus.data.repository.functions.getSections

interface ChoiceQuizDataSource {
    suspend fun getItemsFromFavorites(): List<Country>
    suspend fun getItemsFromAll(): List<Country>
    suspend fun getItemsBySection(sectionId: String): List<Country>
    suspend fun getSections(): List<Section>
    suspend fun getQuizItemResults(countryIds: List<String>): List<QuizItemResult>

    class Default(
        private val dataRepository: Repository
    ) : ChoiceQuizDataSource {
        override suspend fun getItemsFromFavorites() =
            dataRepository.getFlashcardCountriesFavorites()

        override suspend fun getItemsFromAll() =
            dataRepository.getFlashcardCountriesAll()

        override suspend fun getItemsBySection(sectionId: String) =
            dataRepository.getFlashcardCountriesBySection(sectionId)

        override suspend fun getSections() =
            dataRepository.getSections()

        override suspend fun getQuizItemResults(countryIds: List<String>) =
            dataRepository.getQuizItemResults(countryIds)
    }
}


