package com.exploramus.shared.viewmodel.screens.quizzes.choicequiz

import com.exploramus.shared.viewmodel.core.Events
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

fun Events.selectChoiceOption(itemId: String, optionId: String) = screenCoroutine {
    var isAuto = false
    var autoSubmitDelayMs = 0L
    var scheduledRevision = -1

    stateManager.updateScreen(ChoiceQuizScreenState::class) { state ->
        isAuto = state.quiz.config.navigationMode == ChoiceQuizNavigationMode.AUTO
        autoSubmitDelayMs = state.quiz.config.autoSubmitDelayMs
        scheduledRevision = state.totalRestartEvent

        state.copy(quiz = state.quiz.selectOption(itemId, optionId))
    }

    if (isAuto) {
        delay(autoSubmitDelayMs.milliseconds)
        submitChoiceAnswer(itemId, expectedRevision = scheduledRevision)
    }
}

fun Events.submitChoiceAnswer(itemId: String, expectedRevision: Int? = null) = screenCoroutine {
    var didSubmit = false
    var shouldNavigateAuto = false
    var delayMs = 0L
    var scheduledRevision = -1

    stateManager.updateScreen(ChoiceQuizScreenState::class) { state ->
        if (expectedRevision != null && state.totalRestartEvent != expectedRevision) return@updateScreen state

        val (updatedQuiz, submitted) = state.quiz.submitAnswer(itemId)
        didSubmit = submitted

        if (didSubmit) {
            shouldNavigateAuto = state.quiz.config.navigationMode == ChoiceQuizNavigationMode.AUTO
            delayMs = state.quiz.config.autoProceedDelayMs
            scheduledRevision = state.totalRestartEvent
        }

        state.copy(quiz = updatedQuiz)
    }

    if (didSubmit && shouldNavigateAuto) {
        delay(delayMs.milliseconds)
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
        state.copy(
            quiz = state.quiz.restart(),
            isFinished = false,
            totalRestartEvent = state.totalRestartEvent + 1
        )
    }
}
