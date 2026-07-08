package com.exploramus.app.composables.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.exploramus.app.composables.screens.appstart.AppStartupContent
import com.exploramus.shared.viewmodel.core.Navigation
import com.exploramus.shared.viewmodel.core.ScreenIdentifier
import com.exploramus.shared.viewmodel.screens.Level1Navigation
import com.exploramus.shared.viewmodel.screens.home.retryBootstrapApp

@Composable
fun Navigation.Router() {
    val level1Screens = remember { getAllLevel1ScreenIdentifiers() }
    val backStacks = rememberLevel1BackStacks(level1Screens)
    val currentLevel1 = remember { mutableStateOf(navigationState.currentLevel1ScreenIdentifier) }
    val startupState by stateProvider.getAppStartupStateFlow().collectAsStateWithLifecycle()

    AppStartupContent(
        state = startupState,
        onRetry = { events.retryBootstrapApp() }
    ) {
        AppScaffold(
            backStacks = backStacks,
            currentLevel1 = currentLevel1,
        )
        HandleBackButton(
            currentLevel1 = currentLevel1,
            backStacks = backStacks
        )
    }
}

@Composable
private fun Navigation.HandleBackButton(
    currentLevel1: MutableState<ScreenIdentifier>,
    backStacks: Map<String, NavBackStack<Nav3Key>>,
) {
    val activeBackStack = backStacks.getValue(currentLevel1.value.URI)
    val isAtLevel1Root = activeBackStack.size <= 1

    BackHandler(enabled = isAtLevel1Root && !navigationState.nextBackQuitsApp) {
        val originScreenIdentifier = currentLevel1.value
        exitScreen(originScreenIdentifier)
        currentLevel1.value = navigationState.currentLevel1ScreenIdentifier
    }
}

fun getAllLevel1ScreenIdentifiers(): List<ScreenIdentifier> {
    return Level1Navigation.entries.map { it.screenIdentifier }
}

@Composable
private fun rememberLevel1BackStacks(
    level1Screens: List<ScreenIdentifier>
): Map<String, NavBackStack<Nav3Key>> {
    return remember(level1Screens) {
        level1Screens.associate { level1 ->
            level1.URI to NavBackStack(Nav3Key(level1))
        }
    }
}

data class Nav3Key(val screenIdentifier: ScreenIdentifier) : NavKey {
    override fun equals(other: Any?): Boolean =
        other is Nav3Key && other.screenIdentifier.URI == screenIdentifier.URI

    override fun hashCode(): Int = screenIdentifier.URI.hashCode()
}

