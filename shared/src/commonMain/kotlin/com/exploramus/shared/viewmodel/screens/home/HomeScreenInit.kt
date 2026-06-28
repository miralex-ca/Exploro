package com.exploramus.shared.viewmodel.screens.home

import com.exploramus.data.repository.functions.getHomeSections
import com.exploramus.shared.viewmodel.core.CallOnInitValues
import com.exploramus.shared.viewmodel.core.ScreenInitSettings
import com.exploramus.shared.viewmodel.core.StateManager


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