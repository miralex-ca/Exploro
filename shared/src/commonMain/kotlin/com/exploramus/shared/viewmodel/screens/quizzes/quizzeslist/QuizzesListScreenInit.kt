package com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist

import com.exploramus.data.repository.functions.getAllCountriesCount
import com.exploramus.data.repository.functions.getCountriesCountBySection
import com.exploramus.data.repository.functions.getFavoritesCount
import com.exploramus.shared.viewmodel.core.CallOnInitValues
import com.exploramus.shared.viewmodel.core.ScreenInitSettings
import com.exploramus.shared.viewmodel.core.ScreenParams
import com.exploramus.shared.viewmodel.core.StateManager
import kotlinx.serialization.Serializable

@Serializable
data class QuizzesListScreenParams(
    val sectionId: String,
    val sectionType: QuizzesSectionType,
    val screenTitle: String? = null
) : ScreenParams

fun StateManager.initQuizzesListScreen(params: QuizzesListScreenParams) = ScreenInitSettings(
    title = "Quizzes list" ,
    initState = { QuizzesListScreenState(isLoading = true) },
    callOnInit = {

        val itemsCount = when (params.sectionType) {
            QuizzesSectionType.FAVORITES -> dataRepository.getFavoritesCount()
            QuizzesSectionType.ALL_COUNTRIES -> dataRepository.getAllCountriesCount()
            QuizzesSectionType.CONTINENT -> dataRepository.getCountriesCountBySection(params.sectionId)
        }

        val sectionInfo = QuizzesSectionHeaderState(
            title = params.screenTitle ?: "",
            itemsCount = itemsCount.toInt(),
            sectionType = params.sectionType,
            continentId = params.sectionId.takeIf { params.sectionType == QuizzesSectionType.CONTINENT },
        )

        val quizzes = defaultQuizzes()

        updateScreen(QuizzesListScreenState::class) {
            it.copy(
                isLoading = false,
                sectionInfo = sectionInfo,
                quizzes = quizzes,
            )
        }
    },
    callOnInitAtEachNavigation = CallOnInitValues.CALL_BEFORE_SHOWING_SCREEN,
)

fun defaultQuizzes(): List<QuizState> = listOf(
    QuizState(
        quizId = QuizzIds.FLASHCARDS,
        quizType = QuizType.FLASHCARDS,
        title = "Flashcards",
        description = "Browse country cards and reveal capital, flag, or other details at your own pace.",
    ),
    QuizState(
        quizId = QuizzIds.CHOICE_QUIZ_PRIMARY_SECONDARY,
        quizType = QuizType.CHOICE_QUIZ_PRIMARY_SECONDARY,
        title = "Country → Capital",
        description = "Given a country name, choose the correct capital city.",
    ),
    QuizState(
        quizId = QuizzIds.CHOICE_QUIZ_IMAGE_PRIMARY,
        quizType = QuizType.CHOICE_QUIZ_IMAGE_PRIMARY,
        title = "Flag → Country",
        description = "Given a flag, identify which country it belongs to.",
    ),
)