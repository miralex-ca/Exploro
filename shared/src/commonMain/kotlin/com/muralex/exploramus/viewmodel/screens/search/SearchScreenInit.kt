package com.muralex.exploramus.viewmodel.screens.search

import com.muralex.exploramus.viewmodel.core.StateManager
import com.muralex.exploramus.viewmodel.screens.CallOnInitValues
import com.muralex.exploramus.viewmodel.screens.ScreenInitSettings

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