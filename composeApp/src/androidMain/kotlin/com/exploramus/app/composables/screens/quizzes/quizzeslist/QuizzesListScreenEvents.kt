package com.exploramus.app.composables.screens.quizzes.quizzeslist

import com.exploramus.app.composables.navigation.controller.ScreenNavActions
import com.exploramus.shared.viewmodel.screens.Screen
import com.exploramus.shared.viewmodel.screens.quizzes.flashcards.FlashcardScreenParams
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzesSectionType

sealed class QuizzesListUiEvent {
    data class OnQuizClicked(
        val sectionId: String,
        val sectionType: QuizzesSectionType,
        val title: String
    ) : QuizzesListUiEvent()

    data class OnQuizSettingsClicked(val quizId: String) : QuizzesListUiEvent()
}

class QuizzesListEventHandler(
    val navActions: ScreenNavActions
) {
    fun onEvent(event: QuizzesListUiEvent) {
        when (event) {
            is QuizzesListUiEvent.OnQuizClicked -> {
                navActions.appNavController.navigate(
                    Screen.FlashcardsScreen,
                    FlashcardScreenParams(
                        sectionId = event.sectionId,
                        sectionType = event.sectionType,
                        screenTitle = event.title
                    )
                )
            }
            is QuizzesListUiEvent.OnQuizSettingsClicked -> {}
        }
    }
}

