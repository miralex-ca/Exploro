package com.exploramus.shared.viewmodel.screens.search

import com.exploramus.shared.viewmodel.core.CallOnInitValues
import com.exploramus.shared.viewmodel.core.ScreenInitSettings
import com.exploramus.shared.viewmodel.core.StateManager

fun StateManager.initSearchScreen() = ScreenInitSettings(
    title = "Search",
    initState = { _ -> SearchScreenState(isLoading = false) },
    callOnInit = {
        updateScreen(SearchScreenState::class) {
            it.copy(screenBecomeActive = it.screenBecomeActive.trigger())
        }
    },
    clearStateCacheWhenScreenIsRemovedFromBackstack = true,
    callOnInitAtEachNavigation = CallOnInitValues.DONT_CALL,
)