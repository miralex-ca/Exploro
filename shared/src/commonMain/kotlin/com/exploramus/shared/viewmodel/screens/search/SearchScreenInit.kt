package com.exploramus.shared.viewmodel.screens.search

import com.exploramus.shared.viewmodel.core.StateManager
import com.exploramus.shared.viewmodel.core.CallOnInitValues
import com.exploramus.shared.viewmodel.core.ScreenInitSettings

fun StateManager.initSearchScreen() = ScreenInitSettings(
    title = "Search",
    initState = { SearchScreenState(isLoading = false) },
    callOnInit = {
        updateScreen(SearchScreenState::class) {
            it.copy(screenBecomeActive = it.screenBecomeActive.trigger())
        }
    },
    clearStateCacheWhenScreenIsRemovedFromBackstack = true,
    callOnInitAtEachNavigation = CallOnInitValues.DONT_CALL,
)