package com.exploramus.app.composables.screens.quizzes.flashcards

import com.exploramus.app.composables.navigation.controller.ScreenNavActions
import com.exploramus.core.models.FlashcardConfig
import com.exploramus.shared.viewmodel.core.Events
import com.exploramus.shared.viewmodel.screens.quizzes.flashcards.restartFlashcards
import com.exploramus.shared.viewmodel.screens.quizzes.flashcards.toggleFlashcardsSettingsDialog
import com.exploramus.shared.viewmodel.screens.quizzes.flashcards.updateFlashcardConfig

sealed class FlashcardUiEvent  {
    data object OnBackClicked : FlashcardUiEvent()
    data class OnPageChanged(val index: Int) : FlashcardUiEvent()
    data object OnSettingsClicked : FlashcardUiEvent()
    data object OnSettingsDismissed : FlashcardUiEvent()
    data class OnConfigChanged(val config: FlashcardConfig) : FlashcardUiEvent()
    data object OnRestartClicked : FlashcardUiEvent()
}

class FlashcardEventHandler(
    private val navActions: ScreenNavActions,
    private val events: Events,
) {
    fun onEvent(event: FlashcardUiEvent) {
        when (event) {
            is FlashcardUiEvent.OnPageChanged -> {}
            is FlashcardUiEvent.OnConfigChanged -> events.updateFlashcardConfig(event.config)
            FlashcardUiEvent.OnSettingsClicked -> events.toggleFlashcardsSettingsDialog(true)
            FlashcardUiEvent.OnSettingsDismissed -> events.toggleFlashcardsSettingsDialog(false)
            FlashcardUiEvent.OnRestartClicked -> events.restartFlashcards()
            FlashcardUiEvent.OnBackClicked -> navActions.navigateBack()
        }
    }
}

