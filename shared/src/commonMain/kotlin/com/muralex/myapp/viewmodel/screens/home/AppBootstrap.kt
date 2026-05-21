package com.muralex.myapp.viewmodel.screens.home

import com.muralex.data.repository.functions.hasCountriesData
import com.muralex.data.repository.functions.updateCountriesListData
import com.muralex.data.repository.sources.localdb.migrateDbIfNeeded
import com.muralex.models.AppError
import com.muralex.models.DataResult
import com.muralex.models.isSuccess
import com.muralex.myapp.viewmodel.AppStartupState
import com.muralex.myapp.viewmodel.StateManager

suspend fun StateManager.bootstrapApp(): BootstrapResult {
    updateStartupState(AppStartupState.Loading)

    dataRepository.migrateDbIfNeeded()

    val hasLocalData = dataRepository.hasCountriesData()
    val syncResult = dataRepository.updateCountriesListData()

    val isAppUsable = syncResult.isSuccess() || hasLocalData

    return if (isAppUsable) {
        updateStartupState(AppStartupState.Ready)
        BootstrapResult.Success
    } else {
        updateStartupState(AppStartupState.Error(syncResult.toErrorMessage()))
        BootstrapResult.Failure
    }
}

sealed interface BootstrapResult {
    data object Success : BootstrapResult
    data object Failure : BootstrapResult
}

fun BootstrapResult.isFailure() = this == BootstrapResult.Failure

private fun DataResult<*>.toErrorMessage(): String {
    return when {
        this is DataResult.Error && error == AppError.Network -> "Network error"
        else -> "Unable to start app"
    }
}