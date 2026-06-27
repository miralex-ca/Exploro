package com.exploramus.app.composables.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.zIndex
import com.exploramus.app.composables.navigation.controller.ScreenNavActions
import com.exploramus.app.composables.navigation.controller.createAppNavController
import com.exploramus.app.composables.navigation.ui.navigation.Level1BottomBar
import com.exploramus.app.composables.navigation.ui.navigation.Level1NavDrawer
import com.exploramus.app.composables.navigation.ui.navigation.Level1NavRail
import com.exploramus.app.composables.navigation.ui.topbars.TopBarContainer
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.adaptive.useBottomBar
import com.exploramus.app.design.adaptive.useDrawer
import com.exploramus.app.design.adaptive.useNavRail
import com.exploramus.shared.viewmodel.core.Navigation
import com.exploramus.shared.viewmodel.core.NavigationState
import com.exploramus.shared.viewmodel.core.ScreenIdentifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation.AppScaffold(
    saveableStateHolder: SaveableStateHolder,
    localNavigationState: MutableState<NavigationState>
) {
    val screenIdentifier = localNavigationState.value.topScreenIdentifier

    val screenNavActions = remember {
        ScreenNavActions.Default(
            appNavController = createAppNavController(localNavigationState, saveableStateHolder)
        )
    }

    val formFactor = LocalFormFactor.current
    val isLevel1 = screenIdentifier.screen.navigationLevel == 1

    val content = @Composable {
        Scaffold(contentWindowInsets = WindowInsets(0)) { contentPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {
                if (formFactor.useNavRail && isLevel1) {
                    Level1NavRail(
                        modifier = Modifier.zIndex(1f),
                        selectedTab = screenIdentifier,
                        navigateByLevel1Menu = screenNavActions::toLevel1Screen,
                        onSearchClick = screenNavActions::toSearch,
                        onSettingsClick = screenNavActions::toSettings,
                    )
                }

                Column(modifier = Modifier
                    .fillMaxSize()
                    .clipToBounds()
                ) {
                     
                   TopBarContainer(
                       screenIdentifier = screenIdentifier,
                       screenNavActions = screenNavActions
                   )

                    Box(modifier = Modifier.weight(1f)) {
                        ScreenHost(
                            screenIdentifier = screenIdentifier,
                            saveableStateHolder = saveableStateHolder,
                            screenNavActions = screenNavActions
                        )

                        if (formFactor.useBottomBar) {
                            Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                                AnimatedVisibility(
                                    visible = isLevel1,
                                    enter = slideInVertically(
                                        initialOffsetY = { it / 2 },
                                        animationSpec = tween(120)
                                    ) + fadeIn(
                                        initialAlpha = 0.2f,
                                        animationSpec = tween(100)
                                    ),
                                    exit = ExitTransition.None
                                ) {
                                    Level1BottomBar(
                                        selectedTab = screenIdentifier,
                                        navigateByLevel1Menu = screenNavActions::toLevel1Screen,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (formFactor.useDrawer && isLevel1) {
        Level1NavDrawer(
            selectedTab = screenIdentifier,
            navigateByLevel1Menu = screenNavActions::toLevel1Screen,
            onSearchClick = screenNavActions::toSearch,
            onSettingsClick = screenNavActions::toSettings,
            content = content
        )
    } else {
        content()
    }
}

@Composable
private fun Navigation.ScreenHost(
    screenIdentifier: ScreenIdentifier,
    saveableStateHolder: SaveableStateHolder,
    screenNavActions: ScreenNavActions,
) {
    saveableStateHolder.SaveableStateProvider(screenIdentifier.URI) {
        ScreenPicker(
            screenIdentifier = screenIdentifier,
            screenNavActions = screenNavActions,
        )
    }
}


