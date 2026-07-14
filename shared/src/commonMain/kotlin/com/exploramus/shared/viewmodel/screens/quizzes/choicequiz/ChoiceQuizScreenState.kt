package com.exploramus.shared.viewmodel.screens.quizzes.choicequiz

import com.exploramus.shared.viewmodel.core.ScreenState

data class ChoiceQuizScreenState(
    val isLoading: Boolean = false,
    val screenTitle: String = "",
    val quiz: ChoiceQuizState = ChoiceQuizState(),
    val isFinished: Boolean = false,
    val totalRestartEvent: Int = 0,
) : ScreenState
