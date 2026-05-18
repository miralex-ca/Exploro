package com.muralex.myapp.viewmodel

import com.muralex.myapp.viewmodel.appenvironment.AppEnvironment
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StateProvider(val stateManager: StateManager) {
    fun getScreenStateFlow(screenIdentifier: ScreenIdentifier): StateFlow<ScreenState> {
        return stateManager.screenStatesMap[screenIdentifier.URI]!!.asStateFlow()
    }

    fun getLaunchScreenStateFlow(): StateFlow<LaunchScreenState> {
        return stateManager.launchScreenState
    }

    fun getAppEnvironmentFlow(): StateFlow<AppEnvironment> {
        return stateManager.appEnvironment
    }
}