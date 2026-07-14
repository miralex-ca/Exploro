package com.exploramus.shared.viewmodel.screens.quizzes.choicequiz

import com.exploramus.core.models.ChoiceQuizStudyTarget
import com.exploramus.core.models.Country
import com.exploramus.data.repository.functions.getFlashcardCountriesAll
import com.exploramus.data.repository.functions.getFlashcardCountriesBySection
import com.exploramus.data.repository.functions.getFlashcardCountriesFavorites
import com.exploramus.shared.viewmodel.core.CallOnInitValues
import com.exploramus.shared.viewmodel.core.ScreenInitSettings
import com.exploramus.shared.viewmodel.core.ScreenParams
import com.exploramus.shared.viewmodel.core.StateManager
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzesSectionType
import kotlinx.serialization.Serializable

@Serializable
data class ChoiceQuizScreenParams(
    val sectionId: String,
    val sectionType: QuizzesSectionType,
    val screenTitle: String,
    val studyTarget: ChoiceQuizStudyTarget,
    val quizLimit: Int? = null,
) : ScreenParams

fun StateManager.initChoiceQuizScreen(params: ChoiceQuizScreenParams) = ScreenInitSettings(
    title = "Quiz",
    initState = { ChoiceQuizScreenState(isLoading = true) },
    callOnInit = {
        val countries = when (params.sectionType) {
            QuizzesSectionType.FAVORITES -> dataRepository.getFlashcardCountriesFavorites()
            QuizzesSectionType.ALL_COUNTRIES -> dataRepository.getFlashcardCountriesAll()
            QuizzesSectionType.CONTINENT -> dataRepository.getFlashcardCountriesBySection(params.sectionId)
        }

        // We shuffle and apply the limit if provided
        val selectedCountries = countries.shuffled().let { 
            if (params.quizLimit != null) it.take(params.quizLimit) else it 
        }
        
        val quizItems = selectedCountries.map { country ->
            buildChoiceQuizItem(country, countries, params.studyTarget)
        }

        updateScreen(ChoiceQuizScreenState::class) {
            it.copy(
                isLoading = false,
                screenTitle = params.screenTitle,
                quiz = ChoiceQuizState(
                    items = quizItems,
                    config = ChoiceQuizConfig(
                        navigationMode = ChoiceQuizNavigationMode.AUTO
                    )
                )
            )
        }
    },
    callOnInitAtEachNavigation = CallOnInitValues.DONT_CALL,
    clearStateCacheWhenScreenIsRemovedFromBackstack = true,
)

private fun buildChoiceQuizItem(
    target: Country,
    allCountries: List<Country>,
    studyTarget: ChoiceQuizStudyTarget
): ChoiceQuizItemState {
    // 1. Pick distractors (3 other countries)
    val distractors = allCountries
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

    val prompt = when (studyTarget) {
        ChoiceQuizStudyTarget.PRIMARY_SECONDARY -> "What is the capital of this country?"
        ChoiceQuizStudyTarget.SECONDARY_PRIMARY -> "Which country belongs to this capital?"
        ChoiceQuizStudyTarget.IMAGE_PRIMARY -> "Which country does this flag belong to?"
        ChoiceQuizStudyTarget.PRIMARY_IMAGE -> "Find the flag for this country:"
    }

    return ChoiceQuizItemState(
        id = target.id,
        question = ChoiceQuizQuestionState(
            prompt = prompt,
            content = questionContent,
            contentType = questionContentType
        ),
        options = options,
        correctOptionId = target.id
    )
}
