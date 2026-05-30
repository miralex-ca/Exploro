package com.muralex.exploramus.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muralex.exploramus.navigation.appstart.AppStartupContent
import com.muralex.exploramus.viewmodel.core.Navigation
import com.muralex.exploramus.viewmodel.screens.home.retryBootstrapApp

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun Navigation.Router() {
    val screenUIsStateHolder = rememberSaveableStateHolder()
    val localNavigationState = remember { mutableStateOf( navigationState ) }
    val startupState by stateProvider.getAppStartupStateFlow().collectAsStateWithLifecycle()

    AppStartupContent(
        state = startupState,
        onRetry = { events.retryBootstrapApp() }
    ) {
        BoxWithConstraints {
            OnePane(screenUIsStateHolder, localNavigationState)
        }
        HandleBackButton(screenUIsStateHolder, localNavigationState)
    }
}

