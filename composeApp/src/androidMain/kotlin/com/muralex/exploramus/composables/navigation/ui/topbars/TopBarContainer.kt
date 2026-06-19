package com.muralex.exploramus.composables.navigation.ui.topbars

import androidx.compose.runtime.Composable
import com.muralex.exploramus.composables.navigation.controller.ScreenNavActions
import com.muralex.exploramus.resources.Strings
import com.muralex.exploramus.design.adaptive.LocalFormFactor
import com.muralex.exploramus.design.adaptive.useBottomBar
import com.muralex.exploramus.viewmodel.core.Navigation
import com.muralex.exploramus.viewmodel.core.ScreenIdentifier
import com.muralex.exploramus.viewmodel.screens.Screen

@Composable
fun Navigation.TopBarContainer(
    screenIdentifier: ScreenIdentifier,
    screenNavActions: ScreenNavActions,
) {
    val screenTitle = getScreenTitle(screenIdentifier)
    val isLevel1 = screenIdentifier.screen.navigationLevel == 1
    val formFactor = LocalFormFactor.current

    when {
        screenIdentifier.screen == Screen.SearchScreen -> {}
        screenIdentifier.screen == Screen.Lv1SearchScreen -> {}
        isLevel1 -> {
            Level1TopBar(
                title = screenTitle,
                hasActions = formFactor.useBottomBar,
                onSettingsClick = screenNavActions::toSettings,
                onSearchClick = screenNavActions::toSearch,
            )
        }
        screenIdentifier.screen == Screen.CountryDetail -> {}
        else -> {
            TopBar(
                title = screenTitle,
                onBackClick = screenNavActions::navigateBack
            )
        }
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