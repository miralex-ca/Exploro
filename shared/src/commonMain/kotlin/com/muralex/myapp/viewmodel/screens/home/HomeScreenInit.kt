package com.muralex.myapp.viewmodel.screens.home

import com.muralex.data.repository.functions.getHomeSections
import com.muralex.myapp.viewmodel.StateManager
import com.muralex.myapp.viewmodel.screens.CallOnInitValues
import com.muralex.myapp.viewmodel.screens.ScreenInitSettings


fun StateManager.initHomeScreen() = ScreenInitSettings(
    title = "Home",
    initState = { HomeScreenState(isLoading = true) },
    callOnInit = {
        val isFirstRun = !dataRepository.runtimeCache.isBootstrapped

        if (isFirstRun) {
            dataRepository.runtimeCache.isBootstrapped = true
            val bootstrapResult = bootstrapApp()

            if (bootstrapResult.isFailure()) {
                return@ScreenInitSettings
            }
        }

        val sections = dataRepository.getHomeSections()

         updateScreen(HomeScreenState::class) {
            it.copy(
                isLoading = false,
                homeSections = sections
            )
        }
    },
    callOnInitAtEachNavigation = CallOnInitValues.DONT_CALL
)