package com.exploramus.app.composables.screens.quizzes.choicequiz

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.exploramus.app.composables.components.ScreenLoading
import com.exploramus.app.composables.screens.quizzes.choicequiz.views.ChoiceQuizTopBar
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizScreenState

@Composable
fun ChoiceQuizScreen(
    screenState: ChoiceQuizScreenState,
    eventHandler: ChoiceQuizEventHandler,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ChoiceQuizTopBar(
            title = screenState.screenTitle,
            onBackClick = { eventHandler.onEvent(ChoiceQuizUiEvent.OnBackClicked) },
            onEvent = eventHandler::onEvent
        )

        if (screenState.isLoading) {
            ScreenLoading()
        } else {
            // Content will be added later
        }
    }
}
