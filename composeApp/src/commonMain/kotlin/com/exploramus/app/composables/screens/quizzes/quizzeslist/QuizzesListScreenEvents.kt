package com.exploramus.app.composables.screens.quizzes.quizzeslist

import com.exploramus.app.composables.navigation.controller.ScreenNavActions
import com.exploramus.core.models.ChoiceQuizConfig
import com.exploramus.core.models.FlashcardConfig
import com.exploramus.core.models.QuizItemStatus
import com.exploramus.shared.viewmodel.core.*
import com.exploramus.shared.viewmodel.screens.Screen
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.*

sealed class QuizzesListUiEvent {
    data class OnQuizClicked(
        val sectionId: String,
        val sectionType: QuizzesSectionType,
        val title: String,
        val quizType: QuizType
    ) : QuizzesListUiEvent()

    data class OnQuizSettingsClicked(val quizId: String, val quizType: QuizType) : QuizzesListUiEvent()

    data class ToggleFlashcardSettings(val visible: Boolean) : QuizzesListUiEvent()
    data class UpdateFlashcardConfig(val config: FlashcardConfig) : QuizzesListUiEvent()

    data class ToggleChoiceQuizSettings(val visible: Boolean, val quizType: QuizType? = null) : QuizzesListUiEvent()
    data class UpdateChoiceQuizConfig(val config: ChoiceQuizConfig, val quizType: QuizType) : QuizzesListUiEvent()

    data class OnMasteryStatsClicked(
        val sectionId: String,
        val sectionType: QuizzesSectionType,
        val status: QuizItemStatus?,
        val title: String? = null
    ) : QuizzesListUiEvent()

    data class ResetProgress(
        val sectionId: String,
        val sectionType: QuizzesSectionType
    ) : QuizzesListUiEvent()
}

class QuizzesListEventHandler(
    val navActions: ScreenNavActions,
    val events: Events
) {
    fun onEvent(event: QuizzesListUiEvent) {
        when (event) {
            is QuizzesListUiEvent.OnQuizClicked -> {
                when (event.quizType) {
                    QuizType.FLASHCARDS -> {
                        navActions.appNavController.navigate(
                            Screen.FlashcardsScreen,
                            FlashcardScreenParams(
                                sectionId = event.sectionId,
                                sectionType = event.sectionType,
                                screenTitle = event.title
                            )
                        )
                    }
                    QuizType.CHOICE_QUIZ_PRIMARY_SECONDARY,
                    QuizType.CHOICE_QUIZ_IMAGE_PRIMARY -> {
                        navActions.appNavController.navigate(
                            Screen.ChoiceQuizScreen,
                            ChoiceQuizScreenParams(
                                sectionId = event.sectionId,
                                sectionType = event.sectionType,
                                screenTitle = event.title,
                                quizType = event.quizType
                            )
                        )
                    }
                }
            }
            is QuizzesListUiEvent.OnQuizSettingsClicked -> {
                when (event.quizType) {
                    QuizType.FLASHCARDS -> events.toggleFlashcardsSettingsDialog(true)
                    QuizType.CHOICE_QUIZ_PRIMARY_SECONDARY,
                    QuizType.CHOICE_QUIZ_IMAGE_PRIMARY -> events.toggleChoiceQuizSettingsDialog(true, event.quizType)
                }
            }
            is QuizzesListUiEvent.ToggleFlashcardSettings -> {
                events.toggleFlashcardsSettingsDialog(event.visible)
            }
            is QuizzesListUiEvent.UpdateFlashcardConfig -> {
                events.updateFlashcardConfig(event.config)
            }
            is QuizzesListUiEvent.ToggleChoiceQuizSettings -> {
                events.toggleChoiceQuizSettingsDialog(event.visible, event.quizType)
            }
            is QuizzesListUiEvent.UpdateChoiceQuizConfig -> {
                events.updateChoiceQuizConfig(event.config, event.quizType)
            }
            is QuizzesListUiEvent.OnMasteryStatsClicked -> {
                navActions.toGroupedItems(
                    GroupedItemsScreenParams(
                        sectionId = event.sectionId,
                        sectionType = event.sectionType,
                        masteryStatus = event.status,
                        screenTitle = event.title
                    )
                )
            }
            is QuizzesListUiEvent.ResetProgress -> {
                events.resetQuizzesProgress(event.sectionId, event.sectionType)
            }
        }
    }
}
