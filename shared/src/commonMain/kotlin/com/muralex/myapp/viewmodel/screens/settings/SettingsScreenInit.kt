package com.muralex.myapp.viewmodel.screens.settings

import com.muralex.myapp.viewmodel.StateManager
import com.muralex.myapp.viewmodel.screens.CallOnInitValues
import com.muralex.myapp.viewmodel.screens.ScreenInitSettings

fun StateManager.initSettingsScreen() = ScreenInitSettings(
    title = "Settings",
    initState = { SettingsScreenState(isLoading = true) },
    callOnInit = {
        val categories = settingsBuilder.buildCategories()

        updateScreen(SettingsScreenState::class) {
            it.copy(
                isLoading = false,
                categories = categories,
            )
        }
    },
    clearStateCacheWhenScreenIsRemovedFromBackstack = true,
    callOnInitAtEachNavigation = CallOnInitValues.DONT_CALL
)