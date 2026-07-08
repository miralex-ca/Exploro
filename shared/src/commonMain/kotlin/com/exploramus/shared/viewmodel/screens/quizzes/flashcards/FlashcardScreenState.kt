package com.exploramus.shared.viewmodel.screens.quizzes.flashcards

import com.exploramus.shared.viewmodel.core.ScreenState


enum class FlashcardStudyTarget { PRIMARY, SECONDARY, IMAGE }

data class FlashcardScreenState(
    val isLoading: Boolean = false,
    val cards: List<FlashcardState> = emptyList(),
    val currentIndex: Int = 0,
    val studyTarget: FlashcardStudyTarget = FlashcardStudyTarget.IMAGE,
    val isSettingsDialogVisible: Boolean = false,
) : ScreenState

data class FlashcardState(
    val itemId: String,
    val itemName: String,
    val officialName: String,
    val capital: String,
    val flagImage: String,
    val region: String,
)