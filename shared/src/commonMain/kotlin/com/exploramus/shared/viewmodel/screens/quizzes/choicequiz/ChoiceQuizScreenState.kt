package com.exploramus.shared.viewmodel.screens.quizzes.choicequiz

import com.exploramus.shared.viewmodel.core.ScreenState
import com.exploramus.shared.viewmodel.screens.quizzes.common.ChoiceQuizSession

data class ChoiceQuizScreenState(
    val isLoading: Boolean = false,
    val screenTitle: String = "",
    val isFinished: Boolean = false,
    val totalRestartEvent: Int = 0,
    val quizLimit: Int? = null,
    val quiz: ChoiceQuizState = ChoiceQuizState(),
    val session: ChoiceQuizSession? = null,
) : ScreenState
