package com.exploramus.shared.viewmodel.screens.home

import com.exploramus.core.common.result.DataResult
import com.exploramus.core.common.result.isSuccess
import com.exploramus.data.repository.functions.hasCountriesData
import com.exploramus.data.repository.functions.updateCountriesListData
import com.exploramus.data.repository.functions.migrateDbIfNeeded
import com.exploramus.shared.viewmodel.appstate.AppStartupState
import com.exploramus.shared.viewmodel.core.StateManager

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


