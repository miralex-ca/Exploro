package com.exploramus.shared.viewmodel.screens.quizzes.choicequiz

import com.exploramus.core.models.ChoiceQuizNavigationMode
import com.exploramus.core.models.QuizItemResult
import com.exploramus.core.models.QuizResult
import com.exploramus.core.models.update
import com.exploramus.data.repository.functions.getQuizItemResult
import com.exploramus.data.repository.functions.saveQuizItemResult
import com.exploramus.data.repository.functions.saveQuizResult
import com.exploramus.data.repository.functions.updateChoiceQuizNavigationMode
import com.exploramus.shared.viewmodel.core.Events
import kotlinx.coroutines.delay
import kotlin.time.Clock
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
    var isCorrect = false
    var didActuallySubmit = false

    stateManager.updateScreen(ChoiceQuizScreenState::class) { state ->
        if (expectedRevision != null && state.totalRestartEvent != expectedRevision) return@updateScreen state

        val (updatedQuiz, didSubmit) = state.quiz.submitAnswer(itemId)
        
        if (didSubmit) {
            didActuallySubmit = true
            val currentItem = updatedQuiz.items.firstOrNull { it.id == itemId }
            isCorrect = currentItem?.status == ChoiceQuizAnswerStatus.CORRECT
            
            shouldNavigateAuto = updatedQuiz.config.navigationMode == ChoiceQuizNavigationMode.AUTO
            autoProceedDelayMs = if (isCorrect) {
                updatedQuiz.config.autoProceedCorrectDelayMs
            } else {
                updatedQuiz.config.autoProceedIncorrectDelayMs
            }
            scheduledRevision = state.totalRestartEvent
        }

        state.copy(quiz = updatedQuiz)
    }

    if (didActuallySubmit) {
        saveChoiceQuizItemResult(itemId, isCorrect)
    }

    if (shouldNavigateAuto) {
        delay(autoProceedDelayMs.milliseconds)
        nextChoiceQuestion(expectedRevision = scheduledRevision)
    }
}

fun Events.toggleChoiceQuizNavigationMode() = screenCoroutine {
    val state = stateManager.getScreenState(ChoiceQuizScreenState::class) ?: return@screenCoroutine

    val newMode = if (state.quiz.config.navigationMode == ChoiceQuizNavigationMode.MANUAL) {
        ChoiceQuizNavigationMode.AUTO
    } else {
        ChoiceQuizNavigationMode.MANUAL
    }

    dataRepository.updateChoiceQuizNavigationMode(newMode)

    var shouldNavigateAuto = false
    var autoProceedDelayMs = 0L
    var scheduledRevision = -1

    stateManager.updateScreen(ChoiceQuizScreenState::class) { current ->
        val updatedState = current.copy(
            quiz = current.quiz.copy(
                config = current.quiz.config.copy(navigationMode = newMode)
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

fun Events.restartChoiceQuiz() = screenCoroutine {
    val state = stateManager.getScreenState(ChoiceQuizScreenState::class) ?: return@screenCoroutine
    val session = state.session ?: return@screenCoroutine
    val originalQuizId = state.quiz.quizId

    val quizItems = session.buildQuizItems(state.quizLimit)

    val quizState = ChoiceQuizState(
        quizId = originalQuizId,
        items = quizItems,
        config = state.quiz.config,
        currentIndex = 0
    )

    stateManager.updateScreen(ChoiceQuizScreenState::class) { current ->
        if (current.quiz.quizId != originalQuizId) return@updateScreen current

        current.copy(
            quiz = quizState,
            isFinished = false,
            totalRestartEvent = current.totalRestartEvent + 1
        )
    }
}

fun Events.saveChoiceQuizResult(result: QuizResult) = screenCoroutine {
    dataRepository.saveQuizResult(result)
}

fun Events.saveChoiceQuizItemResult(itemId: String, isCorrect: Boolean) = screenCoroutine {
    val timestamp = Clock.System.now().toEpochMilliseconds()
    val existing = dataRepository.getQuizItemResult(itemId) ?: QuizItemResult.empty(itemId)
    val updated = existing.update(isCorrect, timestamp)

    dataRepository.saveQuizItemResult(updated)
}
