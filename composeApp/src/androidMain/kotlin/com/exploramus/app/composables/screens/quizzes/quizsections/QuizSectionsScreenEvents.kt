package com.exploramus.app.composables.screens.quizzes.quizsections

import com.exploramus.app.composables.navigation.controller.QuizListNavParams
import com.exploramus.app.composables.navigation.controller.ScreenNavActions
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzCaategoryId
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzesSectionType

sealed class QuizSectionsUiEvent {
    data object OnFavoritesClicked : QuizSectionsUiEvent()
    data object OnAllCountriesClicked : QuizSectionsUiEvent()
    data class OnContinentClicked(val sectionId: String, val sectionName: String) : QuizSectionsUiEvent()
}

class QuizSectionsEventHandler(
    val navActions: ScreenNavActions
) {
    fun onEvent(event: QuizSectionsUiEvent) {
        when (event) {
            is QuizSectionsUiEvent.OnFavoritesClicked -> {
                navActions.toQuizList(
                    QuizListNavParams(
                        sectionId = QuizzCaategoryId.FAVORITES,
                        quizType = QuizzesSectionType.FAVORITES,
                        name = "Favorites"
                    )
                )
            }
            QuizSectionsUiEvent.OnAllCountriesClicked -> {
                navActions.toQuizList(
                    QuizListNavParams(
                        sectionId = QuizzCaategoryId.ALL_COUNTRIES,
                        quizType = QuizzesSectionType.ALL_COUNTRIES,
                        name = "All countries"
                    )
                )
            }
            is QuizSectionsUiEvent.OnContinentClicked -> {
                navActions.toQuizList(
                    QuizListNavParams(
                        sectionId = event.sectionId,
                        quizType = QuizzesSectionType.CONTINENT,
                        name = event.sectionName
                    )
                )
            }
        }
    }
}

