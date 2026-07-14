package com.exploramus.app.composables.screens.quizzes.choicequiz

import com.exploramus.shared.viewmodel.core.Events
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.nextChoiceQuestion
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.restartChoiceQuiz
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.selectChoiceOption
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.submitChoiceAnswer

sealed interface ChoiceQuizUiEvent {
    data object OnBackClicked : ChoiceQuizUiEvent
    data class OnOptionSelected(val itemId: String, val optionId: String) : ChoiceQuizUiEvent
    data class OnSubmitAnswer(val itemId: String) : ChoiceQuizUiEvent
    data object OnNextClicked : ChoiceQuizUiEvent
    data object OnRestartClicked : ChoiceQuizUiEvent
}

class ChoiceQuizEventHandler(
    private val events: Events,
    private val onBack: () -> Unit
) {
    fun onEvent(event: ChoiceQuizUiEvent) {
        when (event) {
            ChoiceQuizUiEvent.OnBackClicked -> onBack()
            is ChoiceQuizUiEvent.OnOptionSelected -> events.selectChoiceOption(event.itemId, event.optionId)
            is ChoiceQuizUiEvent.OnSubmitAnswer -> events.submitChoiceAnswer(event.itemId)
            ChoiceQuizUiEvent.OnNextClicked -> events.nextChoiceQuestion()
            ChoiceQuizUiEvent.OnRestartClicked -> events.restartChoiceQuiz()
        }
    }
}
