package com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist

import com.exploramus.data.repository.functions.getCountriesCountBySection
import com.exploramus.shared.viewmodel.core.CallOnInitValues
import com.exploramus.shared.viewmodel.core.ScreenInitSettings
import com.exploramus.shared.viewmodel.core.ScreenParams
import com.exploramus.shared.viewmodel.core.StateManager
import kotlinx.serialization.Serializable

@Serializable
data class QuizzesListScreenParams(val sectionId: String, val sectionType: QuizzesSectionType, val screenTitle: String? = null) : ScreenParams

fun StateManager.initQuizzesListScreen(
    params: QuizzesListScreenParams
//    sectionId: String,
//    sectionType: QuizzesSectionType,
//    sectionTitle: String,
) = ScreenInitSettings(
    title = "Quizzes list" ,
    initState = { QuizzesListScreenState(isLoading = true) },
    callOnInit = {

        val itemsCount = dataRepository.getCountriesCountBySection(params.sectionId)

        updateScreen(QuizzesListScreenState::class) {
            it.copy(
                isLoading = false,
                sectionInfo = QuizzesSectionHeaderState(
                    title = params.screenTitle ?: "",
                    itemsCount = itemsCount.toInt(),
                    sectionType = params.sectionType,
                ),
                quizzes = defaultQuizzes(),
            )
        }
    },
    callOnInitAtEachNavigation = CallOnInitValues.CALL_BEFORE_SHOWING_SCREEN,
)

fun defaultQuizzes(): List<QuizState> = listOf(
    QuizState(
        quizId = "flashcards",
        quizType = QuizType.FLASHCARDS,
        title = "Flashcards",
        description = "Browse country cards and reveal capital, flag, or other details at your own pace.",
    ),
    QuizState(
        quizId = "test_country_capital",
        quizType = QuizType.TEST_COUNTRY_CAPITAL,
        title = "Country → Capital",
        description = "Given a country name, choose the correct capital city.",
    ),
    QuizState(
        quizId = "test_flag_country",
        quizType = QuizType.TEST_FLAG_COUNTRY,
        title = "Flag → Country",
        description = "Given a flag, identify which country it belongs to.",
    ),
)