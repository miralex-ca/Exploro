package com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist

import com.exploramus.shared.viewmodel.core.ScreenState

data class QuizzesListScreenState(
    val isLoading: Boolean = false,
    val sectionInfo: QuizzesSectionHeaderState = QuizzesSectionHeaderState(),
    val quizzes: List<QuizState> = emptyList(),
) : ScreenState

data class QuizzesSectionHeaderState(
    val title: String = "",
    val itemsCount: Int = 0,
    val sectionType: QuizzesSectionType = QuizzesSectionType.ALL_COUNTRIES,
)

enum class QuizzesSectionType {
    FAVORITES,
    ALL_COUNTRIES,
    CONTINENT,
}

data class QuizState(
    val quizId: String,
    val quizType: QuizType,
    val title: String,
    val description: String,
)

enum class QuizType {
    FLASHCARDS,
    TEST_COUNTRY_CAPITAL,
    TEST_FLAG_COUNTRY,
}

