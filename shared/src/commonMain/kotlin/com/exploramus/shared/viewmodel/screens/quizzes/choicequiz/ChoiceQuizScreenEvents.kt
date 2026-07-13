package com.exploramus.shared.viewmodel.screens.quizzes.choicequiz

import com.exploramus.shared.viewmodel.core.Events
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

fun Events.selectChoiceOption(itemId: String, optionId: String) = screenCoroutine {
    var isAuto = false
    var autoSubmitDelayMs = 0L

    stateManager.updateScreen(ChoiceQuizScreenState::class) { state ->
        isAuto = state.quiz.config.navigationMode == ChoiceQuizNavigationMode.AUTO
        autoSubmitDelayMs = state.quiz.config.autoSubmitDelayMs

        val updatedItems = state.quiz.items.map { item ->
            if (item.id == itemId && !item.isSubmitted) {
                item.copy(selectedOptionId = optionId)
            } else {
                item
            }
        }
        state.copy(
            quiz = state.quiz.copy(items = updatedItems)
        )
    }

    if (isAuto) {
        delay(autoSubmitDelayMs.milliseconds)
        submitChoiceAnswer(itemId)
    }
}

fun Events.submitChoiceAnswer(itemId: String) = screenCoroutine {
    var didSubmit = false
    var shouldNavigateAuto = false
    var delayMs = 0L

    stateManager.updateScreen(ChoiceQuizScreenState::class) { state ->
        val updatedItems = state.quiz.items.map { item ->
            if (item.id == itemId && item.selectedOptionId != null && !item.isSubmitted) {
                didSubmit = true
                item.copy(isSubmitted = true)
            } else {
                item
            }
        }

        if (didSubmit) {
            shouldNavigateAuto = state.quiz.config.navigationMode == ChoiceQuizNavigationMode.AUTO
            delayMs = state.quiz.config.autoProceedDelayMs
        }

        state.copy(quiz = state.quiz.copy(items = updatedItems))
    }

    if (didSubmit && shouldNavigateAuto) {
        delay(delayMs.milliseconds)
        nextChoiceQuestion()
    }
}

fun Events.nextChoiceQuestion() {
    stateManager.updateScreen(ChoiceQuizScreenState::class) { state ->
        if (state.isFinished) return@updateScreen state

        val nextIndex = state.quiz.currentIndex + 1
        if (nextIndex < state.quiz.items.size) {
            state.copy(
                quiz = state.quiz.copy(currentIndex = nextIndex)
            )
        } else {
            state.copy(
                isFinished = true
            )
        }
    }
}

fun Events.restartChoiceQuiz() {
    stateManager.updateScreen(ChoiceQuizScreenState::class) { state ->
        state.copy(
            quiz = state.quiz.copy(
                currentIndex = 0,
                items = state.quiz.items.map { it.copy(selectedOptionId = null, isSubmitted = false) }
            ),
            isFinished = false,
            totalRestartEvent = state.totalRestartEvent + 1
        )
    }
}
