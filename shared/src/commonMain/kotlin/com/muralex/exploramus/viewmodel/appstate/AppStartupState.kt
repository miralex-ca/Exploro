package com.muralex.exploramus.viewmodel.appstate

sealed interface AppStartupState {
    data object Loading : AppStartupState
    data object Ready : AppStartupState
    sealed interface Failure : AppStartupState {
        data object AfterSync : Failure
        data object UnexpectedError : Failure
    }
}