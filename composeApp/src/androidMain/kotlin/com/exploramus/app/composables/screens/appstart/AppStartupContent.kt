package com.exploramus.app.composables.screens.appstart

import androidx.compose.runtime.Composable
import com.exploramus.shared.viewmodel.appstate.AppStartupState

@Composable
fun AppStartupContent(
    state: AppStartupState,
    onRetry: () -> Unit,
    content: @Composable () -> Unit
) {
    when (state) {
        AppStartupState.Loading ->
            AppLoadingScreen()

        is AppStartupState.Failure ->
            AppErrorScreen(
                failedAfterSync = state is AppStartupState.Failure.AfterSync,
                onRetry = onRetry
            )

        AppStartupState.Ready ->
            content()
    }
}