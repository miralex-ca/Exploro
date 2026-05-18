package com.muralex.myapp.viewmodel.screens.settings

import com.muralex.data.functions.getThemeModeIndex
import com.muralex.myapp.viewmodel.ScreenParams
import com.muralex.myapp.viewmodel.StateManager
import com.muralex.myapp.viewmodel.screens.CallOnInitValues
import com.muralex.myapp.viewmodel.screens.ScreenInitSettings
import kotlinx.serialization.Serializable

@Serializable
data class SettingsParams(val screenTitle: String? = null) : ScreenParams

fun StateManager.initSettingsScreen(params: SettingsParams) = ScreenInitSettings(
    title = params.screenTitle ?: "",
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