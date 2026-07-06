package com.exploramus.shared.viewmodel.screens.quizzes.flashcards

import com.exploramus.shared.viewmodel.core.ScreenState


enum class FlashcardRevealField { CAPITAL, FLAG, REGION }

data class FlashcardScreenState(
    val isLoading: Boolean = false,
    val cards: List<FlashcardState> = emptyList(),
    val currentIndex: Int = 0,
    val revealField: FlashcardRevealField = FlashcardRevealField.CAPITAL,
    val isSettingsDialogVisible: Boolean = false,
) : ScreenState

data class FlashcardState(
    val countryId: String,
    val countryName: String,
    val capital: String,
    val flagEmoji: String,
    val region: String,
)