package com.exploramus.app.composables.screens.quizzes.flashcards

import com.exploramus.app.composables.navigation.controller.ScreenNavActions
import com.exploramus.shared.viewmodel.screens.quizzes.flashcards.FlashcardStudyTarget

sealed class FlashcardUiEvent  {
    // pager page change — to keep currentIndex in sync for the "4 / 32" counter
    data class OnPageChanged(val index: Int) : FlashcardUiEvent()
    data object OnSettingsClicked : FlashcardUiEvent()
    data object OnSettingsDismissed : FlashcardUiEvent()
    data class OnRevealFieldChanged(val field: FlashcardStudyTarget) : FlashcardUiEvent()
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
        }
    }
}

