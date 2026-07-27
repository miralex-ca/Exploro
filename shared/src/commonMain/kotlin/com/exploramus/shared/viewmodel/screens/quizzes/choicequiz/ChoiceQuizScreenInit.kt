package com.exploramus.shared.viewmodel.screens.quizzes.choicequiz

import com.exploramus.data.repository.functions.*
import com.exploramus.shared.viewmodel.core.CallOnInitValues
import com.exploramus.shared.viewmodel.core.ScreenInitSettings
import com.exploramus.shared.viewmodel.core.ScreenParams
import com.exploramus.shared.viewmodel.core.StateManager
import com.exploramus.shared.viewmodel.screens.quizzes.common.QuizCountryManager
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
        val config = when (params.quizType) {
            QuizType.CHOICE_QUIZ_PRIMARY_SECONDARY -> dataRepository.getChoiceQuizPrimarySecondaryConfig()
            QuizType.CHOICE_QUIZ_IMAGE_PRIMARY -> dataRepository.getChoiceQuizImagePrimaryConfig()
            else -> null
        }

        val navigationMode = dataRepository.getChoiceQuizNavigationMode()
        val studyTarget = config?.studyTarget ?: params.quizType.toDefaultStudyTarget()
        val quizLimit = config?.quizLimit

        val countryManager = QuizCountryManager(dataRepository)
        val (countries, selectedCountries) = countryManager.getCountriesForQuiz(
            sectionType = params.sectionType,
            sectionId = params.sectionId,
            quizLimit = quizLimit
        )

        val distractorPoolProvider = countryManager.getDistractorPoolProvider(params.sectionType, countries)
        distractorPoolProvider.preload(selectedCountries)

        val quizItems = selectedCountries.map { country ->
            buildChoiceQuizItem(
                target = country,
                distractorPool = distractorPoolProvider.poolFor(country),
                studyTarget = studyTarget
            )
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
                quizLimit = quizLimit,
                distractorPoolProvider = distractorPoolProvider,
            )
        }
    },
    callOnInitAtEachNavigation = CallOnInitValues.DONT_CALL,
    clearStateCacheWhenScreenIsRemovedFromBackstack = true,
)
