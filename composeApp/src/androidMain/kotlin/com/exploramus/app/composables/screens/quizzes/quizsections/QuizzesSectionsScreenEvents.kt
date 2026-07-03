package com.exploramus.app.composables.screens.quizzes.quizsections

import com.exploramus.app.composables.navigation.controller.QuizListNavParams
import com.exploramus.app.composables.navigation.controller.ScreenNavActions
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzesSectionType

sealed class QuizzesSectionsUiEvent {
    data object OnFavoritesClicked : QuizzesSectionsUiEvent()
    data object OnAllCountriesClicked : QuizzesSectionsUiEvent()
    data class OnContinentClicked(val continentId: String) : QuizzesSectionsUiEvent()
}

class QuizzesSectionsEventHandler(
    val navActions: ScreenNavActions
) {
    fun onEvent(event: QuizzesSectionsUiEvent) {
        when (event) {
            is QuizzesSectionsUiEvent.OnFavoritesClicked -> {
                navActions.toQuizList(
                    QuizListNavParams(
                        sectionId = "favorites",
                        quizType = QuizzesSectionType.FAVORITES,
                        name = "Favorites"
                    )
                )
            }
            QuizzesSectionsUiEvent.OnAllCountriesClicked -> {
                navActions.toQuizList(
                    QuizListNavParams(
                        sectionId = "all",
                        quizType = QuizzesSectionType.ALL_COUNTRIES,
                        name = "All countries"
                    )
                )

            }
            is QuizzesSectionsUiEvent.OnContinentClicked -> {

                navActions.toQuizList(
                    QuizListNavParams(
                        sectionId = event.continentId,
                        quizType = QuizzesSectionType.CONTINENT,
                        name = event.continentId
                    )
                )
            }
        }
    }
}

