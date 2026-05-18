package com.muralex.myapp.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.saveable.SaveableStateHolder
import com.muralex.myapp.viewmodel.Navigation
import com.muralex.myapp.viewmodel.NavigationState
import com.muralex.myapp.viewmodel.ScreenIdentifier
import com.muralex.myapp.viewmodel.ScreenParams
import com.muralex.myapp.viewmodel.screens.Level1Navigation
import com.muralex.myapp.viewmodel.screens.Screen

interface AppNavigator {
    fun navigateBack()
    fun navigate(screen: Screen, screenParams: ScreenParams? = null)
    fun navigateByLevel1(level1Navigation: Level1Navigation)
}

fun Navigation.createAppNavigator(
    localNavigationState: MutableState<NavigationState>,
    saveableStateHolder: SaveableStateHolder
): AppNavigator {
    val navigateFn = navigationProcessor(localNavigationState)
    val navigateLevel1Fn = level1NavigationProcessor(localNavigationState)

    return object : AppNavigator {
        override fun navigateBack() =
            navigateBack(localNavigationState, saveableStateHolder)
        override fun navigate(screen: Screen, screenParams: ScreenParams?) =
            navigateFn(screen, screenParams)
        override fun navigateByLevel1(level1Navigation: Level1Navigation) =
            navigateLevel1Fn(level1Navigation)
    }
}

fun Navigation.navigationProcessor(localNavigationState: MutableState<NavigationState>) : (Screen, ScreenParams?) -> Unit {
    return { screen, screenParams ->
        val screenIdentifier = ScreenIdentifier.get(screen, screenParams)
        navigateToScreen(screenIdentifier) // shared navigationState is updated
        localNavigationState.value = navigationState // update localNavigationState
    }
}

fun Navigation.level1NavigationProcessor(localNavigationState: MutableState<NavigationState>) : (Level1Navigation) -> Unit {
    return {
        selectLevel1Navigation(it.screenIdentifier) // shared navigationState is updated
        localNavigationState.value = navigationState // update localNavigationState
    }
}

private fun Navigation.navigateBack(
    localNavigationState: MutableState<NavigationState>,
    saveableStateHolder: SaveableStateHolder
) {
    val originScreenIdentifier = localNavigationState.value.topScreenIdentifier
    exitScreen(originScreenIdentifier)
    localNavigationState.value = navigationState
    saveableStateHolder.removeState(originScreenIdentifier)
}