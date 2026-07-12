package com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist

import com.exploramus.core.models.ChoiceQuizConfig
import com.exploramus.core.models.FlashcardConfig
import com.exploramus.data.repository.functions.*
import com.exploramus.shared.viewmodel.core.Events

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
