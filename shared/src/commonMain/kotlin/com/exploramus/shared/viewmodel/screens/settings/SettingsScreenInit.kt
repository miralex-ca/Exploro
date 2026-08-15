package com.exploramus.shared.viewmodel.screens.settings

import com.exploramus.shared.viewmodel.core.CallOnInitValues
import com.exploramus.shared.viewmodel.core.ScreenInitSettings
import com.exploramus.shared.viewmodel.core.StateManager

fun StateManager.initSettingsScreen() = ScreenInitSettings(
    title = "Settings",
    initState = { _ -> SettingsScreenState(isLoading = true) },
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