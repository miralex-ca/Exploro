package com.exploramus.app.composables.screens.quizzes.quizzeslist

import com.exploramus.app.composables.navigation.controller.ScreenNavActions

sealed class QuizzesListUiEvent {
    data class OnQuizClicked(val quizId: String) : QuizzesListUiEvent()
    data class OnQuizSettingsClicked(val quizId: String) : QuizzesListUiEvent()
}

class QuizzesListEventHandler(
    val navActions: ScreenNavActions
) {
    fun onEvent(event: QuizzesListUiEvent) {
        when (event) {
            is QuizzesListUiEvent.OnQuizClicked -> {}
            is QuizzesListUiEvent.OnQuizSettingsClicked -> {}
        }
    }
}

