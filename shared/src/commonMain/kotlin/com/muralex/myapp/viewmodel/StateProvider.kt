package com.muralex.myapp.viewmodel

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StateProvider(val stateManager: StateManager) {
    fun getScreenStateFlow(screenIdentifier: ScreenIdentifier): StateFlow<ScreenState> {
        return stateManager.screenStatesMap[screenIdentifier.URI]!!.asStateFlow()
    }

    fun getLaunchScreenStateFlow(): StateFlow<LaunchScreenState> {
        return stateManager.launchScreenState
    }
}