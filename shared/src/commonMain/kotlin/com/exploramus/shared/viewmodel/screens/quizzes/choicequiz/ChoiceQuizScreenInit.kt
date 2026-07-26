package com.exploramus.shared.viewmodel.screens.quizzes.choicequiz

import com.exploramus.data.repository.functions.*
import com.exploramus.shared.viewmodel.core.CallOnInitValues
import com.exploramus.shared.viewmodel.core.ScreenInitSettings
import com.exploramus.shared.viewmodel.core.ScreenParams
import com.exploramus.shared.viewmodel.core.StateManager
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizType
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzesSectionType
import com.exploramus.shared.viewmodel.utils.QuizIdBuilder
import kotlinx.serialization.Serializable

@Serializable
data class ChoiceQuizScreenParams(
    val sectionId: String,
    val sectionType: QuizzesSectionType,
    val screenTitle: String,
    val quizType: QuizType,
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

        val config = when (params.quizType) {
            QuizType.CHOICE_QUIZ_PRIMARY_SECONDARY -> dataRepository.getChoiceQuizPrimarySecondaryConfig()
            QuizType.CHOICE_QUIZ_IMAGE_PRIMARY -> dataRepository.getChoiceQuizImagePrimaryConfig()
            else -> null
        }

        val navigationMode = dataRepository.getChoiceQuizNavigationMode()

        val studyTarget = config?.studyTarget ?: params.quizType.toDefaultStudyTarget()
        val quizLimit = config?.quizLimit

        // We shuffle and apply the limit if provided
        val selectedCountries = countries.shuffled().let { 
            if (quizLimit != null) it.take(quizLimit) else it 
        }
        
        val quizItems = selectedCountries.map { country ->
            buildChoiceQuizItem(country, countries, studyTarget)
        }

        val quizId = QuizIdBuilder.build(
            sectionId = params.sectionId,
            sectionType = params.sectionType,
            quizType = params.quizType,
            studyTarget = studyTarget
        )

        updateScreen(ChoiceQuizScreenState::class) {
            it.copy(
                isLoading = false,
                screenTitle = params.screenTitle,
                quiz = ChoiceQuizState(
                    quizId = quizId,
                    items = quizItems,
                    config = ChoiceQuizConfigState(navigationMode = navigationMode)
                ),
                allCountries = countries,
                studyTarget = studyTarget,
                quizLimit = quizLimit
            )
        }
    },
    callOnInitAtEachNavigation = CallOnInitValues.DONT_CALL,
    clearStateCacheWhenScreenIsRemovedFromBackstack = true,
)
