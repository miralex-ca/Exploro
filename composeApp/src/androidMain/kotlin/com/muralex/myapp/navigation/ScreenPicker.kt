package com.muralex.myapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.muralex.myapp.screens.details.DetailsScreen
import com.muralex.myapp.screens.favorites.FavoritesScreen
import com.muralex.myapp.screens.home.HomeScreen
import com.muralex.myapp.screens.search.SearchScreen
import com.muralex.myapp.screens.section.SectionScreen
import com.muralex.myapp.screens.settings.SettingsScreen
import com.muralex.myapp.viewmodel.Navigation
import com.muralex.myapp.viewmodel.ScreenIdentifier
import com.muralex.myapp.viewmodel.screens.Screen
import com.muralex.myapp.viewmodel.screens.countrydetail.DetailsScreenState
import com.muralex.myapp.viewmodel.screens.favorites.FavoritesScreenState
import com.muralex.myapp.viewmodel.screens.home.HomeScreenState
import com.muralex.myapp.viewmodel.screens.search.SearchScreenState
import com.muralex.myapp.viewmodel.screens.section.SectionScreenState
import com.muralex.myapp.viewmodel.screens.settings.SettingsScreenState

@Composable
fun Navigation.ScreenPicker(
    screenIdentifier: ScreenIdentifier,
    navigator: ScreenNavigator,
) {
    val state by stateProvider.getScreenStateFlow(screenIdentifier).collectAsState()

    val eventHandlers = remember {
        EventHandlers(events = events, navigator = navigator)
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

        else -> {}
    }

}