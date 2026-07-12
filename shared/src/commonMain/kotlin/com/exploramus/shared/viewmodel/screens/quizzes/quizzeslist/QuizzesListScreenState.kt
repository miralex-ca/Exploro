package com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist

import com.exploramus.core.models.FlashcardConfig
import com.exploramus.shared.viewmodel.core.ScreenState

data class QuizzesListScreenState(
    val isLoading: Boolean = false,
    val sectionInfo: QuizzesSectionHeaderState = QuizzesSectionHeaderState(),
    val quizzes: List<QuizState> = emptyList(),
    val flashcardConfig: FlashcardConfig? = null,
) : ScreenState

data class QuizzesSectionHeaderState(
    val title: String = "",
    val itemsCount: Int = 0,
    val sectionType: QuizzesSectionType = QuizzesSectionType.ALL_COUNTRIES,
    val continentId: String? = null,
)

enum class QuizzesSectionType {
    FAVORITES,
    ALL_COUNTRIES,
    CONTINENT,
}


object QuizzCaategoryId {
    val FAVORITES = "favorites"
    val ALL_COUNTRIES = "all_countries"
    val CATEGORY = "category"
}

object QuizzIds {
    val FLASHCARDS = "flashcards"
    val TEST_COUNTRY_CAPITAL = "test_country_capital"
    val TEST_CAPITAL_COUNTRY = "test_capital_country"
    val TEST_COUNTRY_FLAG = "test_country_flag"
    val TEST_FLAG_COUNTRY = "test_flag_country"
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

