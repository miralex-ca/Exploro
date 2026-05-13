package com.muralex.myapp.viewmodel.screens.home

import com.muralex.data.functions.getCountriesListData
import com.muralex.data.functions.getHomeSections
import com.muralex.myapp.viewmodel.StateManager
import com.muralex.myapp.viewmodel.screens.CallOnInitValues
import com.muralex.myapp.viewmodel.screens.ScreenInitSettings


fun StateManager.initHomeScreen() = ScreenInitSettings(
    title = "Home",
    initState = { HomeScreenState(isLoading = true) },
    callOnInit = {
        val isFirstRun = !isBootstrapped

        if (isFirstRun) {
            isBootstrapped = true
            bootstrapApp()
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