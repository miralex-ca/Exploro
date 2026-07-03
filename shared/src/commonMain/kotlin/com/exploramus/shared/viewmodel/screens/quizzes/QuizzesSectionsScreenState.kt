package com.exploramus.shared.viewmodel.screens.quizzes

import com.exploramus.shared.viewmodel.core.ScreenState

data class QuizzesSectionsScreenState(
    val isLoading: Boolean = false,
    val quizzesSections: List<QuizzesSectionState> = emptyList(),
) : ScreenState



sealed class QuizzesSectionState {
    abstract val sectionId: String

    data class Favorites(
        override val sectionId: String = "favorites",
        val itemsCount: Int,
    ) : QuizzesSectionState()

    data class AllCountries(
        override val sectionId: String = "all_countries",
        val itemsCount: Int,
    ) : QuizzesSectionState()

    data class Continents(
        override val sectionId: String = "continents",
        val continents: List<ContinentSectionState>,
    ) : QuizzesSectionState()
}

data class ContinentSectionState(
    val continentId: String,
    val continentName: String,
    val itemsCount: Int,
)

