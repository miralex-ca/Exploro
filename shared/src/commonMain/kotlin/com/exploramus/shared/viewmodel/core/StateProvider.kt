package com.exploramus.shared.viewmodel.core

import com.exploramus.shared.viewmodel.appstate.AppEnvironment
import com.exploramus.shared.viewmodel.appstate.AppStartupState
import com.exploramus.shared.viewmodel.screens.settings.builder.SettingsCategory
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