package com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist

import com.exploramus.core.models.ChoiceQuizConfig
import com.exploramus.core.models.FlashcardConfig
import com.exploramus.core.models.isValidForQuiz
import com.exploramus.data.repository.functions.*
import com.exploramus.shared.viewmodel.core.Events
import com.exploramus.shared.viewmodel.utils.QuizIdBuilder

fun Events.toggleFlashcardsSettingsDialog(visible: Boolean) = screenCoroutine {
    val config = if (visible) dataRepository.getFlashcardConfig() else null
    stateManager.updateScreen(QuizzesListScreenState::class) {
        it.copy(flashcardConfig = config)
    }
}

fun Events.updateFlashcardConfig(config: FlashcardConfig) = screenCoroutine {
    dataRepository.updateFlashcardConfig(config)
    stateManager.updateScreen(QuizzesListScreenState::class) {
        it.copy(flashcardConfig = config)
    }
}

fun Events.toggleChoiceQuizSettingsDialog(visible: Boolean, quizType: QuizType? = null) = screenCoroutine {
    val config = if (visible && quizType != null) {
        when(quizType) {
            QuizType.CHOICE_QUIZ_PRIMARY_SECONDARY -> dataRepository.getChoiceQuizPrimarySecondaryConfig()
            QuizType.CHOICE_QUIZ_IMAGE_PRIMARY -> dataRepository.getChoiceQuizImagePrimaryConfig()
            else -> null
        }
    } else null

    stateManager.updateScreen(QuizzesListScreenState::class) {
        it.copy(
            choiceQuizConfig = config,
            choiceQuizType = if (visible) quizType else null
        )
    }
}

fun Events.updateChoiceQuizConfig(config: ChoiceQuizConfig, quizType: QuizType) = screenCoroutine {
    when(quizType) {
        QuizType.CHOICE_QUIZ_PRIMARY_SECONDARY -> dataRepository.updateChoiceQuizPrimarySecondaryConfig(config)
        QuizType.CHOICE_QUIZ_IMAGE_PRIMARY -> dataRepository.updateChoiceQuizImagePrimaryConfig(config)
        else -> {}
    }
    stateManager.updateScreen(QuizzesListScreenState::class) {
        it.copy(
            choiceQuizConfig = config,
            psTarget = if (quizType == QuizType.CHOICE_QUIZ_PRIMARY_SECONDARY) config.studyTarget else it.psTarget,
            ipTarget = if (quizType == QuizType.CHOICE_QUIZ_IMAGE_PRIMARY) config.studyTarget else it.ipTarget
        )
    }
}

fun Events.resetQuizzesProgress(
    sectionId: String,
    sectionType: QuizzesSectionType
) = screenCoroutine {
    val countries = when (sectionType) {
        QuizzesSectionType.FAVORITES -> dataRepository.getFavorites()
        QuizzesSectionType.ALL_COUNTRIES -> dataRepository.getAllCountries()
        QuizzesSectionType.CONTINENT -> dataRepository.getCountriesBySectionId(sectionId)
    }

    val countryIds = countries.map { it.id }
    dataRepository.deleteQuizItemResults(countryIds)

    val quizIds = listOf(
        QuizIdBuilder.build(sectionId, sectionType, QuizType.FLASHCARDS),
        QuizIdBuilder.build(sectionId, sectionType, QuizType.CHOICE_QUIZ_PRIMARY_SECONDARY),
        QuizIdBuilder.build(sectionId, sectionType, QuizType.CHOICE_QUIZ_IMAGE_PRIMARY),
    )
    dataRepository.deleteQuizResults(quizIds)

    // Refresh state
    val eligibleCountries = countries.filter { it.isValidForQuiz }
    val stats = dataRepository.getSectionStats(eligibleCountries.map { it.id })

    val quizResults = dataRepository.getQuizResults(quizIds).associateBy { it.quizId }

    stateManager.updateScreen(QuizzesListScreenState::class) { state ->
        val updatedQuizzes = state.quizzes.map { quiz ->
            quiz.copy(result = quizResults[quiz.quizId])
        }
        state.copy(
            sectionInfo = state.sectionInfo.copy(
                unknownCount = stats.unknown,
                familiarCount = stats.familiar,
                masteredCount = stats.mastered
            ),
            quizzes = updatedQuizzes
        )
    }
}
