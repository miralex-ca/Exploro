package com.exploramus.app.composables.navigation.ui.topbars

import androidx.compose.runtime.Composable
import com.exploramus.app.composables.navigation.controller.ScreenNavActions
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.adaptive.useBottomBar
import com.exploramus.app.resources.Strings
import com.exploramus.shared.viewmodel.core.Navigation
import com.exploramus.shared.viewmodel.core.ScreenIdentifier
import com.exploramus.shared.viewmodel.screens.Screen

@Composable
fun Navigation.TopBarContainer(
    screenIdentifier: ScreenIdentifier,
    screenNavActions: ScreenNavActions,
) {
    val screen = screenIdentifier.screen
    if (!screen.hasTopBar) return

    val screenTitle = getScreenTitle(screenIdentifier)
    val isLevel1 = screenIdentifier.screen.navigationLevel == 1
    val formFactor = LocalFormFactor.current

    when {
        isLevel1 -> {
            Level1TopBar(
                title = screenTitle,
                hasActions = formFactor.useBottomBar,
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
}

private val Screen.hasTopBar: Boolean
    get() = this !in setOf(
        Screen.SearchScreen,
        Screen.Lv1SearchScreen,
        Screen.CountryDetail,
        Screen.DetailsPagerScreen,
        Screen.FavoritesPagerScreen,
        Screen.FlashcardsScreen,
        Screen.ChoiceQuizScreen,
        Screen.QuizzesListScreen,
    )

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