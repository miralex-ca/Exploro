package com.exploramus.shared.viewmodel.screens.quizzes.flashcards

import com.exploramus.core.models.FlashcardConfig
import com.exploramus.shared.viewmodel.core.ScreenState

data class FlashcardScreenState(
    val isLoading: Boolean = false,
    val screenTitle: String = "",
    val deck: FlashcardDeckState = FlashcardDeckState(),
    val originalCards: List<FlashcardState> = emptyList(),
    val isSettingsDialogVisible: Boolean = false,
    val revision: Int = 0,
) : ScreenState

data class FlashcardDeckState(
    val cards: List<FlashcardState> = emptyList(),
    val config: FlashcardConfig = FlashcardConfig(),
)

data class FlashcardState(
    val itemId: String,
    val itemName: String,
    val officialName: String,
    val capital: String,
    val flagImage: String,
    val region: String,
)
