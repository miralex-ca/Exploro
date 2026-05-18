package com.muralex.myapp.viewmodel

import com.muralex.myapp.viewmodel.appconfig.AppConfig
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StateProvider(val stateManager: StateManager) {
    fun getScreenStateFlow(screenIdentifier: ScreenIdentifier): StateFlow<ScreenState> {
        return stateManager.screenStatesMap[screenIdentifier.URI]!!.asStateFlow()
    }

    fun getLaunchScreenStateFlow(): StateFlow<LaunchScreenState> {
        return stateManager.launchScreenState
    }

    fun getAppConfigStateFlow(): StateFlow<AppConfig> {
        return stateManager.appConfig
    }
}