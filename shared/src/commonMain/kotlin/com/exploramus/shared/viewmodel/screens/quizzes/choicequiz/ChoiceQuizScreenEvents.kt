package com.exploramus.shared.viewmodel.screens.quizzes.choicequiz

import com.exploramus.core.models.ChoiceQuizNavigationMode
import com.exploramus.core.models.QuizResult
import com.exploramus.data.repository.functions.saveQuizResult
import com.exploramus.data.repository.functions.updateChoiceQuizNavigationMode
import com.exploramus.shared.viewmodel.core.Events
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

fun Events.selectChoiceOption(itemId: String, optionId: String) = screenCoroutine {
    var autoSubmitDelayMs = 0L
    var scheduledRevision = -1

    stateManager.updateScreen(ChoiceQuizScreenState::class) { state ->
        autoSubmitDelayMs = state.quiz.config.autoSubmitDelayMs
        scheduledRevision = state.totalRestartEvent

        state.copy(quiz = state.quiz.selectOption(itemId, optionId))
    }

    delay(autoSubmitDelayMs.milliseconds)
    submitChoiceAnswer(itemId, expectedRevision = scheduledRevision)
}

fun Events.submitChoiceAnswer(itemId: String, expectedRevision: Int? = null) = screenCoroutine {
    var shouldNavigateAuto = false
    var autoProceedDelayMs = 0L
    var scheduledRevision = -1

    stateManager.updateScreen(ChoiceQuizScreenState::class) { state ->
        if (expectedRevision != null && state.totalRestartEvent != expectedRevision) return@updateScreen state

        val (updatedQuiz, didSubmit) = state.quiz.submitAnswer(itemId)
        
        if (didSubmit) {
            val currentItem = updatedQuiz.items.firstOrNull { it.id == itemId }
            shouldNavigateAuto = updatedQuiz.config.navigationMode == ChoiceQuizNavigationMode.AUTO
            autoProceedDelayMs = if (currentItem?.status == ChoiceQuizAnswerStatus.CORRECT) {
                updatedQuiz.config.autoProceedCorrectDelayMs
            } else {
                updatedQuiz.config.autoProceedIncorrectDelayMs
            }
            scheduledRevision = state.totalRestartEvent
        }

        state.copy(quiz = updatedQuiz)
    }

    if (shouldNavigateAuto) {
        delay(autoProceedDelayMs.milliseconds)
        nextChoiceQuestion(expectedRevision = scheduledRevision)
    }
}

fun Events.toggleChoiceQuizNavigationMode() = screenCoroutine {
    var shouldNavigateAuto = false
    var autoProceedDelayMs = 0L
    var scheduledRevision = -1

    stateManager.updateScreen(ChoiceQuizScreenState::class) { state ->
        val newMode = if (state.quiz.config.navigationMode == ChoiceQuizNavigationMode.MANUAL) {
            ChoiceQuizNavigationMode.AUTO
        } else {
            ChoiceQuizNavigationMode.MANUAL
        }

        dataRepository.updateChoiceQuizNavigationMode(newMode)
        
        val updatedState = state.copy(
            quiz = state.quiz.copy(
                config = state.quiz.config.copy(navigationMode = newMode)
            )
        )

        val currentItem = updatedState.quiz.items.getOrNull(updatedState.quiz.currentIndex)
        if (newMode == ChoiceQuizNavigationMode.AUTO && currentItem?.isSubmitted == true) {
            shouldNavigateAuto = true
            autoProceedDelayMs = if (currentItem.status == ChoiceQuizAnswerStatus.CORRECT) {
                updatedState.quiz.config.autoProceedCorrectDelayMs
            } else {
                updatedState.quiz.config.autoProceedIncorrectDelayMs
            }
            scheduledRevision = updatedState.totalRestartEvent
        }

        updatedState
    }

    if (shouldNavigateAuto) {
        delay(autoProceedDelayMs.milliseconds)
        nextChoiceQuestion(expectedRevision = scheduledRevision)
    }
}

fun Events.nextChoiceQuestion(expectedRevision: Int? = null) {
    stateManager.updateScreen(ChoiceQuizScreenState::class) { state ->
        if (state.isFinished) return@updateScreen state
        if (expectedRevision != null && state.totalRestartEvent != expectedRevision) return@updateScreen state

        val (updatedQuiz, isFinished) = state.quiz.next()
        state.copy(
            quiz = updatedQuiz,
            isFinished = isFinished
        )
    }
}

fun Events.restartChoiceQuiz() {
    stateManager.updateScreen(ChoiceQuizScreenState::class) { state ->
        // Completely rebuild/reshuffle data
        val selectedCountries = state.allCountries.shuffled().let { 
            if (state.quizLimit != null) it.take(state.quizLimit) else it 
        }
        
        val quizItems = selectedCountries.map { country ->
            buildChoiceQuizItem(country, state.allCountries, state.studyTarget)
        }

        state.copy(
            quiz = ChoiceQuizState(
                quizId = state.quiz.quizId,
                items = quizItems,
                config = state.quiz.config,
                currentIndex = 0
            ),
            isFinished = false,
            totalRestartEvent = state.totalRestartEvent + 1
        )
    }
}

fun Events.saveChoiceQuizResult(result: QuizResult) = screenCoroutine {
    dataRepository.saveQuizResult(result)
}
