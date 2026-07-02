package com.exploramus.app.composables.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exploramus.app.composables.navigation.controller.ScreenNavActions
import com.exploramus.app.composables.navigation.handlers.EventHandlers
import com.exploramus.app.composables.screens.details.DetailsPagerScreen
import com.exploramus.app.composables.screens.details.DetailsScreen
import com.exploramus.app.composables.screens.favorites.FavoritesScreen
import com.exploramus.app.composables.screens.home.HomeScreen
import com.exploramus.app.composables.screens.search.SearchScreen
import com.exploramus.app.composables.screens.section.SectionScreen
import com.exploramus.app.composables.screens.settings.SettingsScreen
import com.exploramus.shared.viewmodel.core.Navigation
import com.exploramus.shared.viewmodel.core.ScreenIdentifier
import com.exploramus.shared.viewmodel.screens.Screen
import com.exploramus.shared.viewmodel.screens.details.detailpager.DetailsPagerScreenState
import com.exploramus.shared.viewmodel.screens.details.singledetail.DetailsScreenState
import com.exploramus.shared.viewmodel.screens.favorites.FavoritesScreenState
import com.exploramus.shared.viewmodel.screens.home.HomeScreenState
import com.exploramus.shared.viewmodel.screens.search.SearchScreenState
import com.exploramus.shared.viewmodel.screens.section.SectionScreenState
import com.exploramus.shared.viewmodel.screens.settings.SettingsScreenState

@Composable
fun Navigation.ScreenPicker(
    screenIdentifier: ScreenIdentifier,
    screenNavActions: ScreenNavActions,
) {
    val state by stateProvider.getScreenStateFlow(screenIdentifier).collectAsStateWithLifecycle()

    val eventHandlers = remember {
        EventHandlers(events = events, navActions = screenNavActions)
    }

    when (screenIdentifier.screen) {
        Screen.HomeScreen ->
            HomeScreen(
                screenState = state as HomeScreenState,
                eventHandler = eventHandlers.home,
            )

        Screen.FavoritesScreen ->
            FavoritesScreen(
                screenState = state as FavoritesScreenState,
                eventHandler = eventHandlers.favorites,
            )

        Screen.SectionScreen ->
            SectionScreen(
                screenState = state as SectionScreenState,
                eventHandler = eventHandlers.section,
            )
        Screen.CountryDetail ->
            DetailsScreen(
                screenState = state as DetailsScreenState,
                eventHandler = eventHandlers.details,
            )

        Screen.DetailsPagerScreen ->
            DetailsPagerScreen(
                screenState = state as DetailsPagerScreenState,
                eventHandler = eventHandlers.detailsPager,
            )

        Screen.SearchScreen ->
            SearchScreen(
                screenState = state as SearchScreenState,
                eventHandler = eventHandlers.search,
            )

        Screen.SettingsScreen ->
            SettingsScreen(
                screenState = state as SettingsScreenState,
                eventHandler = eventHandlers.settings,
            )

        Screen.Lv1SettingsScreen ->
            SettingsScreen(
                screenState = state as SettingsScreenState,
                eventHandler = eventHandlers.settings,
            )

        Screen.Lv1SearchScreen ->
            SearchScreen(
                screenState = state as SearchScreenState,
                eventHandler = eventHandlers.search,
            )
    }
}