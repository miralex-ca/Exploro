package com.exploramus.shared.viewmodel.screens.quizzes.quizsections

import com.exploramus.shared.viewmodel.core.ScreenState

data class QuizSectionsScreenState(
    val isLoading: Boolean = false,
    val quizzesSections: List<QuizSectionState> = emptyList(),
) : ScreenState



sealed class QuizSectionState {
    abstract val sectionId: String

    data class Favorites(
        override val sectionId: String = "favorites",
        val itemsCount: Int,
    ) : QuizSectionState()

    data class AllCountries(
        override val sectionId: String = "all_countries",
        val itemsCount: Int,
    ) : QuizSectionState()

    data class Continents(
        override val sectionId: String = "continents",
        val continents: List<ContinentSectionState>,
    ) : QuizSectionState()
}

data class ContinentSectionState(
    val sectionId: String,
    val sectionName: String,
    val itemsCount: Int,
    val eligibleItemsCount: Int,
    val masteredItemsCount: Int,
) {
    val isAllMastered: Boolean = eligibleItemsCount > 0 && eligibleItemsCount == masteredItemsCount
}

