package com.exploramus.app.composables.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.defaultPopTransitionSpec
import androidx.navigation3.ui.defaultPredictivePopTransitionSpec
import androidx.navigation3.ui.defaultTransitionSpec
import com.exploramus.app.composables.navigation.controller.ScreenNavActions
import com.exploramus.app.composables.navigation.controller.ScreenNavKey
import com.exploramus.app.composables.navigation.ui.navigation.Level1NavRail
import com.exploramus.app.composables.navigation.ui.topbars.TopBarContainer
import com.exploramus.app.composables.navigation.ui.transitions.currentScreenTransitionStyle
import com.exploramus.app.composables.navigation.ui.transitions.rememberScreenTransitions
import com.exploramus.shared.viewmodel.core.Navigation
import com.exploramus.shared.viewmodel.core.ScreenIdentifier

@Composable
fun Navigation.NavigationStackHost(
    activeBackStack: NavBackStack<ScreenNavKey>,
    currentLevel1: ScreenIdentifier,
    screenNavActions: ScreenNavActions,
    useNavRailPlaceholder: Boolean,
) {
    val style = currentScreenTransitionStyle()
    val transitions = rememberScreenTransitions(style)

    if (transitions != null) {
        NavDisplay(
            backStack = activeBackStack,
            onBack = { screenNavActions.navigateBack() },
            transitionSpec = { transitions.push?.invoke(this) ?: defaultTransitionSpec<ScreenNavKey>().invoke(this) },
            popTransitionSpec = { transitions.pop?.invoke(this) ?: defaultPopTransitionSpec<ScreenNavKey>().invoke(this) },
            predictivePopTransitionSpec = { edge -> transitions.predictivePop?.invoke(this, edge) ?: defaultPredictivePopTransitionSpec<ScreenNavKey>().invoke(this, edge) },
            entryProvider = { key ->
                NavEntry(key) {
                    ScreenEntryContent(
                        key = key,
                        currentLevel1 = currentLevel1,
                        screenNavActions = screenNavActions,
                        useNavRailPlaceholder = useNavRailPlaceholder,
                    )
                }
            }
        )
    } else {
        NavDisplay(
            backStack = activeBackStack,
            onBack = { screenNavActions.navigateBack() },
            entryProvider = { key ->
                NavEntry(key) {
                    ScreenEntryContent(
                        key = key,
                        currentLevel1 = currentLevel1,
                        screenNavActions = screenNavActions,
                        useNavRailPlaceholder = useNavRailPlaceholder,
                    )
                }
            }
        )
    }
}

@Composable
private fun Navigation.ScreenEntryContent(
    key: ScreenNavKey,
    currentLevel1: ScreenIdentifier,
    screenNavActions: ScreenNavActions,
    useNavRailPlaceholder: Boolean,
) {
    val isLevel1 = key.screenIdentifier.screen.navigationLevel == 1

    Row(modifier = Modifier.fillMaxSize()) {
        if (useNavRailPlaceholder && isLevel1) {
            NavRailGhostPlaceholder(currentLevel1 = currentLevel1)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            TopBarContainer(screenIdentifier = key.screenIdentifier, screenNavActions = screenNavActions)
            ScreenPicker(screenIdentifier = key.screenIdentifier, screenNavActions = screenNavActions)
        }
    }
}

/**
 * Copy of Level1NavRail, purely to reserve identical width in this
 * entry's own layout pass — kept in sync with the real rail automatically since
 * it's the same composable
 */
@Composable
private fun Navigation.NavRailGhostPlaceholder(
    currentLevel1: ScreenIdentifier
) {
    Level1NavRail(
        modifier = Modifier.pointerInput(Unit) {},
        selectedTab = currentLevel1,
        navigateByLevel1Menu = {},
        onSearchClick = {},
        onSettingsClick = {},
    )
}