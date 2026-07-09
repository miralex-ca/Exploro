package com.exploramus.app.composables.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.zIndex
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
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
import com.exploramus.shared.viewmodel.core.ScreenIdentifier


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation.AppScaffold(
    backStacks: Map<String, NavBackStack<Nav3Key>>,
    currentLevel1: MutableState<ScreenIdentifier>,
) {
    val activeBackStack = backStacks.getValue(currentLevel1.value.URI)
    val screenIdentifier = activeBackStack.lastOrNull()?.screenIdentifier ?: currentLevel1.value

    val screenNavActions = remember {
        ScreenNavActions.Default(
            appNavController = createAppNavController(backStacks, currentLevel1)
        )
    }

    val formFactor = LocalFormFactor.current
    val isLevel1 = screenIdentifier.screen.navigationLevel == 1
    val tabStateHolder = rememberSaveableStateHolder()

    val content = @Composable {
        Scaffold(contentWindowInsets = WindowInsets(0)) { contentPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {


                Column(modifier = Modifier
                    .fillMaxSize()
                    .clipToBounds()
                ) {
                    Box(modifier = Modifier.fillMaxSize().padding(contentPadding)) {
                        if (formFactor.useNavRail) {

                            Row(
                                Modifier.align(Alignment.CenterStart).zIndex(1f)
                            ) {
                                AnimatedVisibility(
                                    visible = isLevel1,
                                    enter = fadeIn(
                                        animationSpec = tween(
                                            durationMillis = NAV_RAIL_APPEAR_DURATION_MS,
                                            delayMillis = NAV_RAIL_APPEAR_DELAY_MS
                                        )
                                    )  ,
                                    exit = fadeOut(animationSpec = tween(NAV_RAIL_APPEAR_DURATION_MS))
                                    ,
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


                        tabStateHolder.SaveableStateProvider(currentLevel1.value.URI) {
                            NavDisplay(
                                backStack = activeBackStack,
                                onBack = { screenNavActions.navigateBack() },
                                transitionSpec = { pushTransition() },   // push
                                popTransitionSpec = { popTransition() }, // pop, can differ from push
                                predictivePopTransitionSpec = { popTransition() },
                                entryProvider = { key ->
                                    NavEntry(key) {

                                        val isLevel1 = key.screenIdentifier.screen.navigationLevel == 1

                                        Row(modifier = Modifier.fillMaxSize()) {
                                            if (formFactor.useNavRail && isLevel1) {
                                                Level1NavRail(
                                                    modifier = Modifier
                                                      //  .alpha(0f)
                                                        .pointerInput(Unit) {}, // absorbs nothing visually, blocks accidental input passthrough
                                                    selectedTab = currentLevel1.value,
                                                    navigateByLevel1Menu = {}, // inert — never actually invoked, this instance is just a spacer
                                                    onSearchClick = {},
                                                    onSettingsClick = {},
                                                )
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
                                }
                            )
                        }

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
                                    exit = fadeOut(
                                        animationSpec = tween(250, delayMillis = 80)
                                    )
                                ) {
                                    Level1BottomBar(
                                        selectedTab = navigationState.currentLevel1ScreenIdentifier,
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

private fun holdThenSnapOut(durationMs: Int): ExitTransition {
    return fadeOut(
        animationSpec = tween(durationMillis = 1, delayMillis = durationMs)
    )
}

private fun pushTransition(durationMs: Int = NAV_TRANSITION_DURATION_MS): ContentTransform {
    return ContentTransform(
        targetContentEnter = fadeIn(animationSpec = tween(durationMs)),
        initialContentExit = holdThenSnapOut(durationMs),
        sizeTransform = SizeTransform { _, _ -> tween(durationMs) }
    )
}

private fun popTransition(durationMs: Int = NAV_TRANSITION_DURATION_MS): ContentTransform {
    return ContentTransform(
        targetContentEnter = EnterTransition.None,
        initialContentExit = fadeOut(animationSpec = tween(durationMs)),
        sizeTransform = SizeTransform { _, _ -> tween(durationMs) }
    )
}


private const val NAV_TRANSITION_DURATION_MS = 350

private const val NAV_RAIL_APPEAR_DELAY_MS = 120
private const val NAV_RAIL_APPEAR_DURATION_MS = 220

