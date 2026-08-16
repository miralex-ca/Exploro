package com.exploramus.app.composables.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.navigation3.runtime.NavBackStack
import com.exploramus.app.composables.navigation.controller.ScreenNavActions
import com.exploramus.app.composables.navigation.controller.ScreenNavActionsDefault
import com.exploramus.app.composables.navigation.controller.ScreenNavKey
import com.exploramus.app.composables.navigation.controller.createAppNavController
import com.exploramus.app.composables.navigation.ui.navigation.Level1BottomBar
import com.exploramus.app.composables.navigation.ui.navigation.Level1NavDrawer
import com.exploramus.app.composables.navigation.ui.navigation.Level1NavRail
import com.exploramus.app.composables.navigation.ui.transitions.bottomBarEnterTransition
import com.exploramus.app.composables.navigation.ui.transitions.bottomBarExitTransition
import com.exploramus.app.composables.navigation.ui.transitions.navRailEnterTransition
import com.exploramus.app.composables.navigation.ui.transitions.navRailExitTransition
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.adaptive.useBottomBar
import com.exploramus.app.design.adaptive.useDrawer
import com.exploramus.app.design.adaptive.useNavRail
import com.exploramus.shared.viewmodel.core.Navigation
import com.exploramus.shared.viewmodel.core.ScreenIdentifier

@Composable
fun Navigation.AppScaffold(
    backStacks: Map<String, NavBackStack<ScreenNavKey>>,
    currentLevel1: MutableState<ScreenIdentifier>,
) {
    val activeBackStack = backStacks.getValue(currentLevel1.value.URI)
    val screenIdentifier = activeBackStack.lastOrNull()?.screenIdentifier ?: currentLevel1.value

    val screenNavActions = remember<ScreenNavActions> {
        ScreenNavActionsDefault(
            appNavController = createAppNavController(backStacks, currentLevel1)
        )
    }

    val formFactor = LocalFormFactor.current
    val isLevel1 = screenIdentifier.screen.navigationLevel == 1
    val tabStateHolder = rememberSaveableStateHolder()

    Scaffold(contentWindowInsets = WindowInsets(0)) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            if (formFactor.useNavRail) {
                Row(
                    Modifier
                        .align(Alignment.CenterStart)
                        .zIndex(1f)
                ) {
                    AnimatedVisibility(
                        visible = isLevel1,
                        enter = navRailEnterTransition,
                        exit = navRailExitTransition,
                    ) {
                        Level1NavRail(
                            selectedTab = currentLevel1.value,
                            navigateByLevel1Menu = screenNavActions::toLevel1Screen,
                            onSearchClick = screenNavActions::toSearch,
                            onSettingsClick = screenNavActions::toSettings,
                        )
                    }
                }
            }

            if (formFactor.useDrawer) {
                Row(
                    Modifier
                        .align(Alignment.CenterStart)
                        .zIndex(1f)
                ) {
                    AnimatedVisibility(
                        visible = isLevel1,
                        enter = navRailEnterTransition, // Reusing rail transitions for now as requested
                        exit = navRailExitTransition,
                    ) {
                        Level1NavDrawer(
                            selectedTab = currentLevel1.value,
                            navigateByLevel1Menu = screenNavActions::toLevel1Screen,
                            onSearchClick = screenNavActions::toSearch,
                            onSettingsClick = screenNavActions::toSettings,
                        )
                    }
                }
            }

            tabStateHolder.SaveableStateProvider(currentLevel1.value.URI) {
                NavigationStackHost(
                    activeBackStack = activeBackStack,
                    currentLevel1 = currentLevel1.value,
                    screenNavActions = screenNavActions,
                    usePersistentNavPanelPlaceholder = formFactor.useNavRail || formFactor.useDrawer,
                )
            }

            if (formFactor.useBottomBar) {
                Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                    AnimatedVisibility(
                        visible = isLevel1,
                        enter = bottomBarEnterTransition,
                        exit = bottomBarExitTransition
                    ) {
                        Level1BottomBar(
                            selectedTab = currentLevel1.value,
                            navigateByLevel1Menu = screenNavActions::toLevel1Screen,
                        )
                    }
                }
            }
        }
    }
}
