package com.exploramus.shared.viewmodel.screens.quizzes.common

import com.exploramus.core.models.ChoiceQuizStudyTarget
import com.exploramus.core.models.Country
import com.exploramus.core.models.QuizItemResult
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizContentType
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizItemState
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizOptionState
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizQuestionState
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzesSectionType

class ChoiceQuizManager(
    private val dataSource: ChoiceQuizDataSource
) {
    suspend fun getAllCountries(sectionType: QuizzesSectionType, sectionId: String): List<Country> =
        when (sectionType) {
            QuizzesSectionType.FAVORITES -> dataSource.getItemsFromFavorites()
            QuizzesSectionType.ALL_COUNTRIES -> dataSource.getItemsFromAll()
            QuizzesSectionType.CONTINENT -> dataSource.getItemsBySection(sectionId)
        }

    fun getDistractorPoolProvider(
        sectionType: QuizzesSectionType,
        allCountries: List<Country>
    ): DistractorPoolProvider {
        return when (sectionType) {
            QuizzesSectionType.FAVORITES,
            QuizzesSectionType.ALL_COUNTRIES -> SectionDistractorPoolProvider(dataSource)
            QuizzesSectionType.CONTINENT -> SameListDistractorPoolProvider(allCountries)
        }
    }

    /**
     * Selects target countries prioritized by the least mastery ranking.
     * This ensures that items the user is less familiar with appear more often in quizzes.
     */
    suspend fun selectTargetItems(
        allItems: List<Country>,
        limit: Int?
    ): List<Country> {
        if (allItems.isEmpty()) return emptyList()

        val results = dataSource.getQuizItemResults(allItems.map { it.id })
        val resultsMap = results.associateBy { it.id }

        val prioritized = allItems
            .shuffled()
            .map { item ->
                val result = resultsMap[item.id] ?: QuizItemResult.empty(item.id)
                item to result.masteryRating
            }
            .sortedBy { it.second }
            .map { it.first }

        val selection = if (limit != null && limit > 0) prioritized.take(limit) else prioritized
        return selection.shuffled()
    }
}

fun buildChoiceQuizItem(
    target: Country,
    distractorPool: List<Country>,
    studyTarget: ChoiceQuizStudyTarget
): ChoiceQuizItemState {
    val distractors = distractorPool
        .filter { it.id != target.id }
        .shuffled()
        .take(3)

    // 2. Build options based on studyTarget
    val options = (distractors + target).shuffled().map { country ->
        val content = when (studyTarget) {
            ChoiceQuizStudyTarget.PRIMARY_SECONDARY -> country.capital
            ChoiceQuizStudyTarget.SECONDARY_PRIMARY -> country.name
            ChoiceQuizStudyTarget.IMAGE_PRIMARY -> country.name
            ChoiceQuizStudyTarget.PRIMARY_IMAGE -> country.flagImage
        }
        val contentType = if (studyTarget == ChoiceQuizStudyTarget.PRIMARY_IMAGE) {
            ChoiceQuizContentType.IMAGE
        } else {
            ChoiceQuizContentType.TEXT
        }
        ChoiceQuizOptionState(id = country.id, content = content, contentType = contentType)
    }

    // 3. Build question based on studyTarget
    val questionContent = when (studyTarget) {
        ChoiceQuizStudyTarget.PRIMARY_SECONDARY -> target.name
        ChoiceQuizStudyTarget.SECONDARY_PRIMARY -> target.capital
        ChoiceQuizStudyTarget.IMAGE_PRIMARY -> target.flagImage
        ChoiceQuizStudyTarget.PRIMARY_IMAGE -> target.name
    }

    val questionContentType = if (studyTarget == ChoiceQuizStudyTarget.IMAGE_PRIMARY) {
        ChoiceQuizContentType.IMAGE
    } else {
        ChoiceQuizContentType.TEXT
    }

    return ChoiceQuizItemState(
        id = target.id,
        question = ChoiceQuizQuestionState(
            content = questionContent,
            contentType = questionContentType,
            studyTarget = studyTarget,
        ),
        options = options,
        correctOptionId = target.id
    )
}
