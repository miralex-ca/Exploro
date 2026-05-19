package com.muralex.myapp.viewmodel.screens.home


import com.muralex.data.repository.functions.updateCountriesListData
import com.muralex.data.repository.sources.localdb.migrateDbIfNeeded
import com.muralex.myapp.viewmodel.LaunchScreenState
import com.muralex.myapp.viewmodel.StateManager


suspend fun StateManager.bootstrapApp() {
    dataRepository.migrateDbIfNeeded()
    dataRepository.updateCountriesListData()
    updateLaunchScreenState(
        LaunchScreenState.INACTIVE
    )
}

