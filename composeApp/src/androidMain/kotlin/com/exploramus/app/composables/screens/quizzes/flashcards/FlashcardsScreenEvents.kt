package com.exploramus.app.composables.screens.quizzes.flashcards

import com.exploramus.app.composables.navigation.controller.ScreenNavActions
import com.exploramus.core.models.FlashcardStudyTarget

sealed class FlashcardUiEvent  {
    object OnBackClicked : FlashcardUiEvent()
    data class OnPageChanged(val index: Int) : FlashcardUiEvent()
    data object OnSettingsClicked : FlashcardUiEvent()
    data object OnSettingsDismissed : FlashcardUiEvent()
    data class OnRevealFieldChanged(val field: FlashcardStudyTarget) : FlashcardUiEvent()
    data object OnShuffleClicked : FlashcardUiEvent()
    data object OnRestartClicked : FlashcardUiEvent()
    data object OnRevealDetailsClicked : FlashcardUiEvent()
}

class FlashcardEventHandler(
    val navActions: ScreenNavActions
) {
    fun onEvent(event: FlashcardUiEvent) {
        when (event) {
            is FlashcardUiEvent.OnPageChanged -> {}
            is FlashcardUiEvent.OnRevealFieldChanged -> {}
            FlashcardUiEvent.OnSettingsClicked -> {}
            FlashcardUiEvent.OnSettingsDismissed -> {}
            FlashcardUiEvent.OnShuffleClicked -> {}
            FlashcardUiEvent.OnRestartClicked -> {}
            FlashcardUiEvent.OnRevealDetailsClicked -> {}
            FlashcardUiEvent.OnBackClicked -> navActions.navigateBack()
        }
    }
}

