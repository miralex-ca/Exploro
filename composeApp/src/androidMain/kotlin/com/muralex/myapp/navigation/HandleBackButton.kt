package com.muralex.myapp.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.saveable.SaveableStateHolder
import com.muralex.myapp.viewmodel.Navigation
import com.muralex.myapp.viewmodel.NavigationState
import com.muralex.myapp.viewmodel.debugLogger

@Composable
fun Navigation.HandleBackButton(
    saveableStateHolder: SaveableStateHolder,
    localNavigationState: MutableState<NavigationState>
) {
    BackHandler(!localNavigationState.value.nextBackQuitsApp) { // catching the back button
        val navState = localNavigationState.value
        val originScreenIdentifier = navState.topScreenIdentifier

        debugLogger.log("HandleBackButton: originScreenIdentifier URI -> "+originScreenIdentifier.URI)

        exitScreen(originScreenIdentifier) // shared navigationState is updated
        localNavigationState.value = navigationState // update localNavigationState
        saveableStateHolder.removeState(originScreenIdentifier)
    }
}