package com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist

import com.exploramus.core.models.FlashcardConfig
import com.exploramus.data.repository.functions.getFlashcardConfig
import com.exploramus.data.repository.functions.updateFlashcardConfig
import com.exploramus.shared.viewmodel.core.Events

fun Events.toggleFlashcardsSettingsDialog(visible: Boolean) = screenCoroutine {
    val config = if (visible) dataRepository.getFlashcardConfig() else null
    stateManager.updateScreen(QuizzesListScreenState::class) {
        it.copy(flashcardConfig = config)
    }
}

fun Events.updateFlashcardConfig(config: FlashcardConfig) = screenCoroutine {
    dataRepository.updateFlashcardConfig(config)
    stateManager.updateScreen(QuizzesListScreenState::class) {
        it.copy(flashcardConfig = config)
    }
}
