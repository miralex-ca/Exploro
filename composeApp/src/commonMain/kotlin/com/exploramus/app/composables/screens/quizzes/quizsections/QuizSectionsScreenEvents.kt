package com.exploramus.app.composables.screens.quizzes.quizsections

import com.exploramus.app.composables.navigation.controller.QuizListNavParams
import com.exploramus.app.composables.navigation.controller.ScreenNavActions
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzesSectionType
import com.exploramus.shared.viewmodel.utils.QuizCollectionIds

sealed class QuizSectionsUiEvent {
    data class OnFavoritesClicked(val sectionName: String) : QuizSectionsUiEvent()
    data class OnAllCountriesClicked(val sectionName: String) : QuizSectionsUiEvent()
    data class OnContinentClicked(val sectionId: String, val sectionName: String) : QuizSectionsUiEvent()
}

class QuizSectionsEventHandler(
    val navActions: ScreenNavActions,
) {
    fun onEvent(event: QuizSectionsUiEvent) {
        when (event) {
            is QuizSectionsUiEvent.OnFavoritesClicked -> {
                navActions.toQuizList(
                    QuizListNavParams(
                        sectionId = QuizCollectionIds.FAVORITES,
                        quizType = QuizzesSectionType.FAVORITES,
                        name = event.sectionName
                    )
                )
            }
            is QuizSectionsUiEvent.OnAllCountriesClicked -> {
                navActions.toQuizList(
                    QuizListNavParams(
                        sectionId = QuizCollectionIds.ALL,
                        quizType = QuizzesSectionType.ALL_COUNTRIES,
                        name = event.sectionName
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
