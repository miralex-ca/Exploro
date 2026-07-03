package com.exploramus.shared.viewmodel.screens.quizzes

import com.exploramus.shared.viewmodel.core.ScreenState

data class QuizzesSectionsScreenState(
    val isLoading: Boolean = false,
    val quizzesSections: List<QuizzesSectionState> = emptyList(),
) : ScreenState

data class QuizzesSectionState(
    val sectionId: String,
    val sectionName: String,
    val itemsCount: Int,
)

