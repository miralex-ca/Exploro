package com.muralex.exploramus.viewmodel.screens.home

import com.muralex.data.repository.functions.getHomeSections
import com.muralex.exploramus.viewmodel.core.StateManager
import com.muralex.exploramus.viewmodel.core.CallOnInitValues
import com.muralex.exploramus.viewmodel.core.ScreenInitSettings


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

        val sections = dataRepository.getHomeSections().toHomeSectionStates()

         updateScreen(HomeScreenState::class) {
            it.copy(
                isLoading = false,
                homeSections = sections
            )
        }
    },
    callOnInitAtEachNavigation = CallOnInitValues.DONT_CALL
)