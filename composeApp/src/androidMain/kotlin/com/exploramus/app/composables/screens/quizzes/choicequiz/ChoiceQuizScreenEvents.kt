package com.exploramus.app.composables.screens.quizzes.choicequiz

import com.exploramus.app.composables.navigation.controller.ScreenNavActions
import com.exploramus.core.models.QuizResult
import com.exploramus.shared.viewmodel.core.Events
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.*

sealed interface ChoiceQuizUiEvent {
    data object OnBackClicked : ChoiceQuizUiEvent
    data class OnOptionSelected(val itemId: String, val optionId: String) : ChoiceQuizUiEvent
    data class OnSubmitAnswer(val itemId: String) : ChoiceQuizUiEvent
    data object OnNextClicked : ChoiceQuizUiEvent
    data object OnRestartClicked : ChoiceQuizUiEvent
    data object OnToggleNavigationMode : ChoiceQuizUiEvent
    data class OnSubmitQuizResult(val result: QuizResult) : ChoiceQuizUiEvent
}

class ChoiceQuizEventHandler(
    private val navActions: ScreenNavActions,
    private val events: Events,
) {
    fun onEvent(event: ChoiceQuizUiEvent) {
        when (event) {
            ChoiceQuizUiEvent.OnBackClicked -> navActions.navigateBack()
            is ChoiceQuizUiEvent.OnOptionSelected -> events.selectChoiceOption(event.itemId, event.optionId)
            is ChoiceQuizUiEvent.OnSubmitAnswer -> events.submitChoiceAnswer(event.itemId)
            ChoiceQuizUiEvent.OnNextClicked -> events.nextChoiceQuestion()
            ChoiceQuizUiEvent.OnRestartClicked -> events.restartChoiceQuiz()
            ChoiceQuizUiEvent.OnToggleNavigationMode -> events.toggleChoiceQuizNavigationMode()
            is ChoiceQuizUiEvent.OnSubmitQuizResult -> events.saveChoiceQuizResult(event.result)
        }
    }
}
