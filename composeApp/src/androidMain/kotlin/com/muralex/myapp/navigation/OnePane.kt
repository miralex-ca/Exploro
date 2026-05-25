package com.muralex.myapp.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.ui.Modifier
import com.muralex.myapp.navigation.bars.Level1BottomBar
import com.muralex.myapp.navigation.bars.Level1TopBar
import com.muralex.myapp.navigation.bars.TopBar
import com.muralex.myapp.utils.Strings
import com.muralex.myapp.viewmodel.Navigation
import com.muralex.myapp.viewmodel.NavigationState
import com.muralex.myapp.viewmodel.ScreenIdentifier
import com.muralex.myapp.viewmodel.screens.Screen


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

    Scaffold(
        contentWindowInsets = WindowInsets(0),
        content = { contentPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {
                when {
                    screenIdentifier.screen == Screen.SearchScreen -> {}
                    screenIdentifier.screen.navigationLevel == 1 -> {
                        Level1TopBar(
                            title = screenTitle,
                            onSettingsClick = screenNavActions::toSettings,
                            onSearchClick = screenNavActions::toSearch
                        )
                    }

                    else -> {
                        TopBar(screenTitle, onBackClick = screenNavActions::navigateBack)
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
        },
        bottomBar = {
            if (screenIdentifier.screen.navigationLevel == 1)
                Level1BottomBar(
                    selectedTab = screenIdentifier,
                    navigateByLevel1Menu = screenNavActions::toLevel1Screen,
                )
        }
    )
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

