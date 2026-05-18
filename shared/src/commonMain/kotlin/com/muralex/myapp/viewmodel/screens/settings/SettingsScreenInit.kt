package com.muralex.myapp.viewmodel.screens.settings

import com.muralex.data.functions.getThemeModeIndex
import com.muralex.myapp.viewmodel.StateManager
import com.muralex.myapp.viewmodel.screens.CallOnInitValues
import com.muralex.myapp.viewmodel.screens.ScreenInitSettings

fun StateManager.initSettingsScreen() = ScreenInitSettings(
    title = "Settings",
    initState = { SettingsScreenState(isLoading = true) },
    callOnInit = {

        val savedThemeModeIndex = dataRepository.getThemeModeIndex()

         updateScreen(SettingsScreenState::class) {
            it.copy(
                isLoading = false,
                savedThemeMode = savedThemeModeIndex
            )
        }
    },
    clearStateCacheWhenScreenIsRemovedFromBackstack = true,
    callOnInitAtEachNavigation = CallOnInitValues.DONT_CALL

)