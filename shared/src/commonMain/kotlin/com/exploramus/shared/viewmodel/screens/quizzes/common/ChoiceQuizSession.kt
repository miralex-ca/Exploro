package com.exploramus.shared.viewmodel.screens.quizzes.common

import com.exploramus.core.models.ChoiceQuizStudyTarget
import com.exploramus.core.models.Country
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizItemState
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzesSectionType

class ChoiceQuizSession private constructor(
    private val quizManager: ChoiceQuizManager,
    private val distractorPoolProvider: DistractorPoolProvider,
    val allItems: List<Country>,
    val studyTarget: ChoiceQuizStudyTarget,
) {
    /**
     * Selects target countries, preloads their distractor pools, and builds quiz items.
     * Safe to call repeatedly (e.g. on restart) — reuses the already-fetched allCountries
     * and the already-constructed distractorPoolProvider (its internal cache persists).
     */
    suspend fun buildQuizItems(quizLimit: Int?): List<ChoiceQuizItemState> {
        val selectedCountries = quizManager.selectTargetItems(allItems, quizLimit)
        distractorPoolProvider.preload(selectedCountries)
        return selectedCountries.map { country ->
            buildChoiceQuizItem(
                target = country,
                distractorPool = distractorPoolProvider.poolFor(country),
                studyTarget = studyTarget
            )
        }
    }

    companion object {
        suspend fun create(
            dataSource: ChoiceQuizDataSource,
            sectionType: QuizzesSectionType,
            sectionId: String,
            studyTarget: ChoiceQuizStudyTarget,
        ): ChoiceQuizSession {
            val countryManager = ChoiceQuizManager(dataSource)
            val allCountries = countryManager.getAllCountries(sectionType, sectionId)
            val distractorPoolProvider = countryManager.getDistractorPoolProvider(sectionType, allCountries)
            return ChoiceQuizSession(countryManager, distractorPoolProvider, allCountries, studyTarget)
        }
    }
}