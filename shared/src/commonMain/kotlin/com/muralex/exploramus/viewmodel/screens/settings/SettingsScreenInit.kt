package com.muralex.exploramus.viewmodel.screens.settings

import com.muralex.exploramus.viewmodel.core.CallOnInitValues
import com.muralex.exploramus.viewmodel.core.ScreenInitSettings
import com.muralex.exploramus.viewmodel.core.StateManager

fun StateManager.initSettingsScreen() = ScreenInitSettings(
    title = "Settings",
    initState = { SettingsScreenState(isLoading = true) },
    callOnInit = {
        val categories = settingsManager.getCategories()

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