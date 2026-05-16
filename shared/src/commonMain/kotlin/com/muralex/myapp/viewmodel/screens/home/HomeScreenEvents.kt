package com.muralex.myapp.viewmodel.screens.home


import com.muralex.data.functions.updateCountriesListData
import com.muralex.data.sources.localdb.migrateDbIfNeeded
import com.muralex.myapp.viewmodel.LaunchScreenState
import com.muralex.myapp.viewmodel.StateManager


suspend fun StateManager.bootstrapApp() {
    dataRepository.migrateDbIfNeeded()
    dataRepository.updateCountriesListData()
    updateLaunchScreenState(
        LaunchScreenState.INACTIVE
    )
}

