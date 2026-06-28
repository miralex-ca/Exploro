package com.exploramus.app.composables.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exploramus.app.composables.screens.appstart.AppStartupContent
import com.exploramus.shared.viewmodel.core.Navigation
import com.exploramus.shared.viewmodel.core.NavigationState
import com.exploramus.shared.viewmodel.screens.home.retryBootstrapApp

@Composable
fun Navigation.Router() {
    val screenUIsStateHolder = rememberSaveableStateHolder()
    val localNavigationState = remember { mutableStateOf( navigationState ) }
    val startupState by stateProvider.getAppStartupStateFlow().collectAsStateWithLifecycle()

    AppStartupContent(
        state = startupState,
        onRetry = { events.retryBootstrapApp() }
    ) {

        AppScaffold(screenUIsStateHolder, localNavigationState)

        HandleBackButton(screenUIsStateHolder, localNavigationState)
    }
}

@Composable
private fun Navigation.HandleBackButton(
    saveableStateHolder: SaveableStateHolder,
    localNavigationState: MutableState<NavigationState>
) {
    BackHandler(!localNavigationState.value.nextBackQuitsApp) { // catching the back button
        val navState = localNavigationState.value
        val originScreenIdentifier = navState.topScreenIdentifier
        exitScreen(originScreenIdentifier) // shared navigationState is updated
        localNavigationState.value = navigationState // update localNavigationState
        saveableStateHolder.removeState(originScreenIdentifier)
    }
}

