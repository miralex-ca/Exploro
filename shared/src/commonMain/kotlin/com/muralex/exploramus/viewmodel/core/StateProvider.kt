package com.muralex.exploramus.viewmodel.core

import com.muralex.exploramus.viewmodel.appstate.AppEnvironment
import com.muralex.exploramus.viewmodel.appstate.AppStartupState
import com.muralex.exploramus.viewmodel.screens.settings.builder.SettingsCategory
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StateProvider(val stateManager: StateManager) {
    fun getScreenStateFlow(screenIdentifier: ScreenIdentifier): StateFlow<ScreenState> {
        return stateManager.screenStatesMap[screenIdentifier.URI]!!.asStateFlow()
    }

    fun getAppStartupStateFlow(): StateFlow<AppStartupState> {
        return stateManager.appStartupState
    }

    fun getAppEnvironmentFlow(): StateFlow<AppEnvironment> {
        return stateManager.appEnvironment
    }

    fun getSettingsFlow(): StateFlow<List<SettingsCategory>> {
        return stateManager.settingsManager.settings
    }
}