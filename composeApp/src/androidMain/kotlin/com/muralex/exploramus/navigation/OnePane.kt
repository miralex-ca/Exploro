package com.muralex.exploramus.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.muralex.exploramus.navigation.bars.Level1TopBar
import com.muralex.exploramus.navigation.bars.TopBar
import com.muralex.exploramus.navigation.bars.navigation.Level1BottomBar
import com.muralex.exploramus.navigation.bars.navigation.Level1NavDrawer
import com.muralex.exploramus.navigation.bars.navigation.Level1NavRail
import com.muralex.exploramus.resources.Strings
import com.muralex.exploramus.ui.adaptive.FormFactor
import com.muralex.exploramus.ui.adaptive.LocalFormFactor
import com.muralex.exploramus.ui.adaptive.useBottomBar
import com.muralex.exploramus.ui.adaptive.useDrawer
import com.muralex.exploramus.ui.adaptive.useNavRail
import com.muralex.exploramus.viewmodel.core.Navigation
import com.muralex.exploramus.viewmodel.core.NavigationState
import com.muralex.exploramus.viewmodel.core.ScreenIdentifier
import com.muralex.exploramus.viewmodel.screens.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation.OnePane(
    saveableStateHolder: SaveableStateHolder,
    localNavigationState: MutableState<NavigationState>
) {
    val screenIdentifier = localNavigationState.value.topScreenIdentifier
    val screenTitle = getScreenTitle(screenIdentifier)

    val screenNavActions = remember {
        ScreenNavActions.Default(createAppNavController(localNavigationState, saveableStateHolder))
    }

    val isLevel1 = screenIdentifier.screen.navigationLevel == 1

    val formFactor = LocalFormFactor.current

    val applyScroll = formFactor == FormFactor.PHONE_LANDSCAPE //isCompactHeight
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val content = @Composable {
        Scaffold(
            modifier = if (applyScroll) Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
            else Modifier
                ,
            contentWindowInsets = WindowInsets(0),
            bottomBar = {
                if (formFactor.useBottomBar && isLevel1) {
                    Level1BottomBar(
                        selectedTab = screenIdentifier,
                        navigateByLevel1Menu = screenNavActions::toLevel1Screen,
                    )
                }
            }
        ) { contentPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {
                if (formFactor.useNavRail && isLevel1) {
                    Level1NavRail(
                        selectedTab = screenIdentifier,
                        navigateByLevel1Menu = screenNavActions::toLevel1Screen,
                        onSearchClick = screenNavActions::toSearch,
                        onSettingsClick = screenNavActions::toSettings,
                    )
                }

                Column(modifier = Modifier.fillMaxSize()) {
                    when {
                        screenIdentifier.screen == Screen.SearchScreen -> {}
                        isLevel1 -> {

                            Level1TopBar(
                                title = screenTitle,
                                hasActions = formFactor == FormFactor.PHONE_PORTRAIT,
                                scrollBehavior = if (applyScroll) scrollBehavior else null,
                                onSettingsClick = screenNavActions::toSettings,
                                onSearchClick = screenNavActions::toSearch,

                            )
                        }
                        else -> {
                            TopBar(
                                title = screenTitle,
                                onBackClick = screenNavActions::navigateBack
                            )
                        }
                    }

                    Row {
                        saveableStateHolder.SaveableStateProvider(screenIdentifier.URI) {
                            ScreenPicker(
                                screenIdentifier = screenIdentifier,
                                screenNavActions = screenNavActions,
                            )
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
fun Navigation.getScreenTitle(screenIdentifier: ScreenIdentifier): String {
    val screenInitSettings = screenIdentifier.getScreenInitSettings(stateManager)

    return when (screenIdentifier.screen) {
        Screen.HomeScreen -> Strings.homeTitle
        Screen.FavoritesScreen -> Strings.favoritesTitle
        Screen.SearchScreen -> Strings.searchTitle
        Screen.SettingsScreen -> Strings.settingsTitle
        else -> screenInitSettings.title
    }
}
