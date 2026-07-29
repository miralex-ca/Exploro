package com.exploramus.shared.viewmodel.screens.quizzes.groupeditems

import com.exploramus.core.models.QuizItemStatus
import com.exploramus.shared.viewmodel.core.ScreenState

data class GroupedItemsScreenState(
    val isLoading: Boolean = false,
    val title: String = "",
    val items: List<MasteryItemState> = emptyList(),
    val masteryStatus: QuizItemStatus? = null,
) : ScreenState

data class MasteryItemState(
    val id: String,
    val name: String,
    val flagEmoji: String,
    val flagImage: String,
    val subregion: String,
    val status: QuizItemStatus,
)
