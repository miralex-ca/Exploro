package com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist

import com.exploramus.data.repository.functions.*
import com.exploramus.shared.viewmodel.core.CallOnInitValues
import com.exploramus.shared.viewmodel.core.ScreenInitSettings
import com.exploramus.shared.viewmodel.core.ScreenParams
import com.exploramus.shared.viewmodel.core.StateManager
import com.exploramus.shared.viewmodel.utils.QuizIdBuilder
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

        val countries = when (params.sectionType) {
            QuizzesSectionType.FAVORITES -> dataRepository.getFavorites()
            QuizzesSectionType.ALL_COUNTRIES -> dataRepository.getAllCountries()
            QuizzesSectionType.CONTINENT -> dataRepository.getCountriesBySectionId(params.sectionId)
        }

        val stats = dataRepository.getSectionStats(countries.map { it.id })

        val sectionInfo = QuizzesSectionHeaderState(
            title = params.screenTitle ?: "",
            itemsCount = countries.size,
            sectionType = params.sectionType,
            continentId = params.sectionId.takeIf { params.sectionType == QuizzesSectionType.CONTINENT },
            unknownCount = stats.unknown,
            familiarCount = stats.familiar,
            masteredCount = stats.mastered
        )

        val psConfig = dataRepository.getChoiceQuizPrimarySecondaryConfig()
        val ipConfig = dataRepository.getChoiceQuizImagePrimaryConfig()

        val quizzes = defaultQuizzes(params)
        val quizIds = quizzes.map { it.quizId }
        val quizResults = dataRepository.getQuizResults(quizIds).associateBy { it.quizId }

        val quizzesWithResults = quizzes.map { quiz ->
            quiz.copy(result = quizResults[quiz.quizId])
        }

        updateScreen(QuizzesListScreenState::class) {
            it.copy(
                isLoading = false,
                sectionInfo = sectionInfo,
                quizzes = quizzesWithResults,
                psTarget = psConfig.studyTarget,
                ipTarget = ipConfig.studyTarget
            )
        }
    },
    callOnInitAtEachNavigation = CallOnInitValues.CALL_BEFORE_SHOWING_SCREEN,
)

fun defaultQuizzes(params: QuizzesListScreenParams): List<QuizState> = listOf(
    QuizState(
        quizId = QuizIdBuilder.build(params.sectionId, params.sectionType, QuizType.FLASHCARDS),
        quizType = QuizType.FLASHCARDS,
    ),
    QuizState(
        quizId = QuizIdBuilder.build(params.sectionId, params.sectionType, QuizType.CHOICE_QUIZ_PRIMARY_SECONDARY),
        quizType = QuizType.CHOICE_QUIZ_PRIMARY_SECONDARY,
    ),
    QuizState(
        quizId = QuizIdBuilder.build(params.sectionId, params.sectionType, QuizType.CHOICE_QUIZ_IMAGE_PRIMARY),
        quizType = QuizType.CHOICE_QUIZ_IMAGE_PRIMARY,
    ),
)
