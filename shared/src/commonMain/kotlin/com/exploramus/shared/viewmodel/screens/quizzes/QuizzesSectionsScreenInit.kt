package com.exploramus.shared.viewmodel.screens.quizzes

import com.exploramus.shared.viewmodel.core.CallOnInitValues
import com.exploramus.shared.viewmodel.core.ScreenInitSettings
import com.exploramus.shared.viewmodel.core.StateManager


fun StateManager.initQuizzesSectionsScreen() = ScreenInitSettings(
    title = "Quizzes",
    initState = { QuizzesSectionsScreenState(isLoading = true) },
    callOnInit = {


        updateScreen(QuizzesSectionsScreenState::class) {
            it.copy(
                isLoading = false,
                quizzesSections = emptyList()
            )
        }
    },
    callOnInitAtEachNavigation = CallOnInitValues.CALL_BEFORE_SHOWING_SCREEN
)