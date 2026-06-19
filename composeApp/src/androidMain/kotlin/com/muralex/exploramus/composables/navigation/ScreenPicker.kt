package com.muralex.exploramus.composables.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.muralex.exploramus.composables.navigation.controller.ScreenNavActions
import com.muralex.exploramus.composables.navigation.handlers.EventHandlers
import com.muralex.exploramus.composables.screens.details.DetailsScreen
import com.muralex.exploramus.composables.screens.favorites.FavoritesScreen
import com.muralex.exploramus.composables.screens.home.HomeScreen
import com.muralex.exploramus.composables.screens.search.SearchScreen
import com.muralex.exploramus.composables.screens.section.SectionScreen
import com.muralex.exploramus.composables.screens.settings.SettingsScreen
import com.muralex.exploramus.viewmodel.core.Navigation
import com.muralex.exploramus.viewmodel.core.ScreenIdentifier
import com.muralex.exploramus.viewmodel.screens.Screen
import com.muralex.exploramus.viewmodel.screens.countrydetail.DetailsScreenState
import com.muralex.exploramus.viewmodel.screens.favorites.FavoritesScreenState
import com.muralex.exploramus.viewmodel.screens.home.HomeScreenState
import com.muralex.exploramus.viewmodel.screens.search.SearchScreenState
import com.muralex.exploramus.viewmodel.screens.section.SectionScreenState
import com.muralex.exploramus.viewmodel.screens.settings.SettingsScreenState

@Composable
fun Navigation.ScreenPicker(
    screenIdentifier: ScreenIdentifier,
    screenNavActions: ScreenNavActions,
) {
    val state by stateProvider.getScreenStateFlow(screenIdentifier).collectAsState()

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

        else -> {}
    }

}