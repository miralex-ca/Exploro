package com.exploramus.shared.viewmodel.screens.quizzes.common

import com.exploramus.core.models.Country
import com.exploramus.core.models.QuizItemResult
import com.exploramus.data.repository.Repository
import com.exploramus.data.repository.functions.getFlashcardCountriesAll
import com.exploramus.data.repository.functions.getFlashcardCountriesBySection
import com.exploramus.data.repository.functions.getFlashcardCountriesFavorites
import com.exploramus.data.repository.functions.getQuizItemResults
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzesSectionType

class QuizCountryManager(private val dataRepository: Repository) {

    /**
     * Loads all countries for a given section and selects a subset based on mastery and limit.
     * Returns a Pair of (all countries in section, selected target countries).
     */
    suspend fun getCountriesForQuiz(
        sectionType: QuizzesSectionType,
        sectionId: String,
        quizLimit: Int?
    ): Pair<List<Country>, List<Country>> {
        val allCountries = when (sectionType) {
            QuizzesSectionType.FAVORITES -> dataRepository.getFlashcardCountriesFavorites()
            QuizzesSectionType.ALL_COUNTRIES -> dataRepository.getFlashcardCountriesAll()
            QuizzesSectionType.CONTINENT -> dataRepository.getFlashcardCountriesBySection(sectionId)
        }

        val selectedCountries = selectTargetCountries(allCountries, quizLimit)

        return allCountries to selectedCountries
    }

    /**
     * Returns a DistractorPoolProvider appropriate for the given section type.
     */
    fun getDistractorPoolProvider(
        sectionType: QuizzesSectionType,
        allCountries: List<Country>
    ): DistractorPoolProvider {
        return when (sectionType) {
            QuizzesSectionType.FAVORITES,
            QuizzesSectionType.ALL_COUNTRIES -> SectionDistractorPoolProvider(dataRepository)
            QuizzesSectionType.CONTINENT -> SameListDistractorPoolProvider(allCountries)
        }
    }

    /**
     * Selects target countries prioritized by the least mastery ranking.
     * This ensures that items the user is less familiar with appear more often in quizzes.
     */
    private suspend fun selectTargetCountries(
        countries: List<Country>,
        limit: Int?
    ): List<Country> {
        if (countries.isEmpty()) return emptyList()

        // Fetch existing results to determine mastery
        val results = dataRepository.getQuizItemResults(countries.map { it.id })
        val resultsMap = results.associateBy { it.id }

        // 1. Shuffle all countries first to randomize tie-breaking for items with identical mastery.
        // 2. Sort by mastery rating (ascending = least mastered first).
        val prioritized = countries
            .shuffled() 
            .map { country ->
                val result = resultsMap[country.id] ?: QuizItemResult.empty(country.id)
                country to result.masteryRating
            }
            .sortedBy { it.second }
            .map { it.first }

        // 3. Take the requested limit.
        val selection = if (limit != null && limit > 0) {
            prioritized.take(limit)
        } else {
            prioritized
        }

        // 4. Shuffle the final selection so that the quiz presentation order is random.
        return selection.shuffled()
    }
}
