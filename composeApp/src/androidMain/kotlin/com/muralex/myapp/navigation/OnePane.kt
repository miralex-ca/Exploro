package com.muralex.myapp.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

    val navigator = remember { createNavigator(localNavigationState, saveableStateHolder) }

    Scaffold(
        content = { contentPadding ->
            val adjustedPadding = PaddingValues(
                top = 0.dp,
                bottom = contentPadding.calculateBottomPadding()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(adjustedPadding)
            ) {
                when {
                    screenIdentifier.screen == Screen.SearchScreen -> {}
                    screenIdentifier.screen.navigationLevel == 1 -> {
                        Level1TopBar(
                            title = screenTitle,
                            navigate = navigationProcessor(localNavigationState),
                        )
                    }

                    else -> {
                        TopBar(screenTitle, onBackClick = navigator::navigateBack)
                    }
                }

                Row {
                    saveableStateHolder.SaveableStateProvider(screenIdentifier.URI) {
                        ScreenPicker(
                            screenIdentifier = screenIdentifier,
                            navigator = navigator,
                            navigate = navigationProcessor(localNavigationState),
                            navigateByLevel1 = level1NavigationProcessor(localNavigationState),
                            navigateBack = navigator::navigateBack
                        )
                    }
                }
            }
        },
        bottomBar = {
            if (screenIdentifier.screen.navigationLevel == 1)
                Level1BottomBar(
                    selectedTab = screenIdentifier,
                    navigateByLevel1Menu = level1NavigationProcessor(localNavigationState)
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
        else -> screenInitSettings.title
    }
}

