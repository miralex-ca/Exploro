package com.exploramus.app.composables.screens.quizzes.quizzeslist

import com.exploramus.app.composables.navigation.controller.ScreenNavActions
import com.exploramus.core.models.FlashcardConfig
import com.exploramus.shared.viewmodel.core.Events
import com.exploramus.shared.viewmodel.screens.Screen
import com.exploramus.shared.viewmodel.screens.quizzes.flashcards.FlashcardScreenParams
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.*

sealed class QuizzesListUiEvent {
    data class OnQuizClicked(
        val sectionId: String,
        val sectionType: QuizzesSectionType,
        val title: String
    ) : QuizzesListUiEvent()

    data class OnQuizSettingsClicked(val quizId: String) : QuizzesListUiEvent()

    data class ToggleFlashcardSettings(val visible: Boolean) : QuizzesListUiEvent()
    data class UpdateFlashcardConfig(val config: FlashcardConfig) : QuizzesListUiEvent()
}

class QuizzesListEventHandler(
    val navActions: ScreenNavActions,
    val events: Events
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
            is QuizzesListUiEvent.OnQuizSettingsClicked -> {
                if (event.quizId == QuizzIds.FLASHCARDS) {
                    events.toggleFlashcardsSettingsDialog(true)
                }
            }
            is QuizzesListUiEvent.ToggleFlashcardSettings -> {
                events.toggleFlashcardsSettingsDialog(event.visible)
            }
            is QuizzesListUiEvent.UpdateFlashcardConfig -> {
                events.updateFlashcardConfig(event.config)
            }
        }
    }
}

