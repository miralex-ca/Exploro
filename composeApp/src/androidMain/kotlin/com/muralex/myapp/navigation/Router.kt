package com.muralex.myapp.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muralex.myapp.navigation.appstart.AppErrorScreen
import com.muralex.myapp.navigation.appstart.AppLoadingScreen
import com.muralex.myapp.viewmodel.AppStartupState
import com.muralex.myapp.viewmodel.Navigation
import com.muralex.myapp.viewmodel.screens.home.retryBootstrapApp

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun Navigation.Router() {
    val screenUIsStateHolder = rememberSaveableStateHolder()
    val localNavigationState = remember { mutableStateOf( navigationState ) }
    val startupState by stateProvider.getAppStartupStateFlow().collectAsStateWithLifecycle()

    when (startupState) {
        is AppStartupState.Loading -> AppLoadingScreen()

        is AppStartupState.Failure -> {
            AppErrorScreen(
                failedAfterSync = startupState is AppStartupState.Failure.AfterSync,
                onRetry = { events.retryBootstrapApp() }
            )
        }

        AppStartupState.Ready -> {
            BoxWithConstraints {
                OnePane(screenUIsStateHolder, localNavigationState)
            }
            HandleBackButton(screenUIsStateHolder, localNavigationState)
        }
    }
}

