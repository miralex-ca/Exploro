package com.exploramus.shared.viewmodel.screens.quizzes.flashcards

import com.exploramus.core.models.FlashcardConfig
import com.exploramus.data.repository.functions.updateFlashcardConfig
import com.exploramus.shared.viewmodel.core.Events

fun Events.toggleFlashcardsSettingsDialog(visible: Boolean) {
    stateManager.updateScreen(FlashcardScreenState::class) {
        it.copy(isSettingsDialogVisible = visible)
    }
}

fun Events.updateFlashcardConfig(config: FlashcardConfig) = screenCoroutine {
    dataRepository.updateFlashcardConfig(config)
    stateManager.updateScreen(FlashcardScreenState::class) {
        it.copy(
            deck = it.deck.copy(
                config = config,
                cards = if (config.shuffleEnabled) it.originalCards.shuffled() else it.originalCards
            ),
            revision = it.revision + 1
        )
    }
}

fun Events.restartFlashcards() = screenCoroutine {
    stateManager.updateScreen(FlashcardScreenState::class) {
        it.copy(
            deck = it.deck.copy(
                cards = if (it.deck.config.shuffleEnabled) it.originalCards.shuffled() else it.originalCards
            ),
            revision = it.revision + 1
        )
    }
}
