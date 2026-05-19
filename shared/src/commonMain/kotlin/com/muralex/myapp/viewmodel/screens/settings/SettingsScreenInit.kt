package com.muralex.myapp.viewmodel.screens.settings

import com.muralex.data.repository.functions.getThemeMode
import com.muralex.myapp.viewmodel.StateManager
import com.muralex.myapp.viewmodel.screens.CallOnInitValues
import com.muralex.myapp.viewmodel.screens.ScreenInitSettings

fun StateManager.initSettingsScreen() = ScreenInitSettings(
    title = "Settings",
    initState = { SettingsScreenState(isLoading = true) },
    callOnInit = {

        val savedThemeMode = dataRepository.getThemeMode()

         updateScreen(SettingsScreenState::class) {
            it.copy(
                isLoading = false,
                savedThemeMode = savedThemeMode
            )
        }
    },
    clearStateCacheWhenScreenIsRemovedFromBackstack = true,
    callOnInitAtEachNavigation = CallOnInitValues.DONT_CALL

)