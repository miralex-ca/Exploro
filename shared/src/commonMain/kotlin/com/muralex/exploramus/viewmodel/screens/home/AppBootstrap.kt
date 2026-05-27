package com.muralex.exploramus.viewmodel.screens.home

import com.muralex.core.common.result.DataResult
import com.muralex.core.common.result.isSuccess
import com.muralex.data.repository.functions.hasCountriesData
import com.muralex.data.repository.functions.updateCountriesListData
import com.muralex.data.repository.sources.localdb.migrateDbIfNeeded
import com.muralex.exploramus.viewmodel.AppStartupState
import com.muralex.exploramus.viewmodel.StateManager

sealed interface BootstrapResult {
    data object Success : BootstrapResult
    data object Failure : BootstrapResult
}

fun BootstrapResult.isFailure() = this == BootstrapResult.Failure

suspend fun StateManager.bootstrapApp(): BootstrapResult {
    updateStartupState(AppStartupState.Loading)

    val migrationResult = dataRepository.migrateDbIfNeeded()

    if (migrationResult is DataResult.Error) {
        updateStartupState(AppStartupState.Failure.UnexpectedError)
        return BootstrapResult.Failure
    }

    val hasLocalData = dataRepository.hasCountriesData()
    val syncResult = dataRepository.updateCountriesListData()

    val isAppUsable = syncResult.isSuccess() || hasLocalData

    return if (isAppUsable) {
        updateStartupState(AppStartupState.Ready)
        BootstrapResult.Success
    } else {
        updateStartupState(AppStartupState.Failure.AfterSync)
        BootstrapResult.Failure
    }
}


