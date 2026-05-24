package com.muralex.myapp.viewmodel.screens.settings

import com.muralex.myapp.viewmodel.StateManager
import com.muralex.myapp.viewmodel.screens.CallOnInitValues
import com.muralex.myapp.viewmodel.screens.ScreenInitSettings

fun StateManager.initSettingsScreen() = ScreenInitSettings(
    title = "Settings",
    initState = { SettingsScreenState(isLoading = true) },
    callOnInit = {
        val settingsBuilder = SettingsBuilder(dataRepository)

        val settings = settingsBuilder.build()
        val categories = settingsBuilder.buildCategories()

        updateScreen(SettingsScreenState::class) {
            it.copy(
                isLoading = false,
                settings = settings,
                settingsBuilder = settingsBuilder,
                categories = categories,
            )
        }
    },
    clearStateCacheWhenScreenIsRemovedFromBackstack = true,
    callOnInitAtEachNavigation = CallOnInitValues.DONT_CALL
)