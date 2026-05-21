package com.muralex.myapp.viewmodel.screens.search

import com.muralex.myapp.viewmodel.StateManager
import com.muralex.myapp.viewmodel.screens.CallOnInitValues
import com.muralex.myapp.viewmodel.screens.ScreenInitSettings

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