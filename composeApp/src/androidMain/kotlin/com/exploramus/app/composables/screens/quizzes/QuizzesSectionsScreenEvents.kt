package com.exploramus.app.composables.screens.quizzes

import com.exploramus.app.composables.navigation.controller.ScreenNavActions

sealed class QuizzesSectionsUiEvent {
    data object OnFavoritesClicked : QuizzesSectionsUiEvent()
    data object OnAllCountriesClicked : QuizzesSectionsUiEvent()
    data class OnContinentClicked(val continentId: String) : QuizzesSectionsUiEvent()
}

class QuizzesSectionsEventHandler(
    val navActions: ScreenNavActions
) {
    fun onEvent(event: QuizzesSectionsUiEvent) {
        when (event) {
            is QuizzesSectionsUiEvent.OnFavoritesClicked -> { }
            QuizzesSectionsUiEvent.OnAllCountriesClicked -> { }
            is QuizzesSectionsUiEvent.OnContinentClicked -> { }
        }
    }
}

