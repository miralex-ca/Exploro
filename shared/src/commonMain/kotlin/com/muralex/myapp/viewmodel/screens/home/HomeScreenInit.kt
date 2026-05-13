package com.muralex.myapp.viewmodel.screens.home

import com.muralex.data.functions.getCountriesListData
import com.muralex.myapp.viewmodel.StateManager
import com.muralex.myapp.viewmodel.screens.CallOnInitValues
import com.muralex.myapp.viewmodel.screens.ScreenInitSettings


fun StateManager.initHomeScreen() = ScreenInitSettings(
    title = "Home",
    initState = { HomeScreenState(isLoading = true) },
    callOnInit = {
        val isFirstRun = !isBootstrapped

        println("Home: Called on init")

        if (isFirstRun) {
            isBootstrapped = true
            bootstrapApp()
        }

        val listData = dataRepository.getCountriesListData()

         updateScreen(HomeScreenState::class) {
            it.copy(
                isLoading = false,
                countriesListItems = listData
            )
        }
    },
    callOnInitAtEachNavigation = CallOnInitValues.DONT_CALL

)