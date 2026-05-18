package com.muralex.myapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.muralex.myapp.screens.CountriesListScreen
import com.muralex.myapp.screens.settings.SettingsScreen
import com.muralex.myapp.screens.details.DetailsEventHandler
import com.muralex.myapp.screens.details.DetailsScreen
import com.muralex.myapp.screens.favorites.FavoritesEventHandler
import com.muralex.myapp.screens.favorites.FavoritesScreen
import com.muralex.myapp.screens.home.HomeEventHandler
import com.muralex.myapp.screens.home.HomeScreen
import com.muralex.myapp.screens.search.SearchEventHandler
import com.muralex.myapp.screens.search.SearchScreen
import com.muralex.myapp.screens.section.SectionEventHandler
import com.muralex.myapp.screens.section.SectionScreen
import com.muralex.myapp.screens.settings.SettingsEventHandler
import com.muralex.myapp.viewmodel.Navigation
import com.muralex.myapp.viewmodel.ScreenIdentifier
import com.muralex.myapp.viewmodel.ScreenParams
import com.muralex.myapp.viewmodel.screens.Level1Navigation
import com.muralex.myapp.viewmodel.screens.Screen
import com.muralex.myapp.viewmodel.screens.countriesList.CountriesListState
import com.muralex.myapp.viewmodel.screens.countrydetail.CountryDetailParams
import com.muralex.myapp.viewmodel.screens.countrydetail.DetailsScreenState
import com.muralex.myapp.viewmodel.screens.favorites.FavoritesScreenState
import com.muralex.myapp.viewmodel.screens.home.HomeScreenState
import com.muralex.myapp.viewmodel.screens.search.SearchScreenState
import com.muralex.myapp.viewmodel.screens.section.SectionScreenState
import com.muralex.myapp.viewmodel.screens.settings.SettingsScreenState
import com.muralex.myapp.viewmodel.screens.settings.setThemeModeByIndex

@Composable
fun Navigation.ScreenPicker(
    screenIdentifier: ScreenIdentifier,
    navigator: AppNavigator,
    navigate: (Screen, ScreenParams?) -> Unit,
    navigateByLevel1: (Level1Navigation) -> Unit,
    navigateBack: () -> Unit,
) {
    val state by stateProvider.getScreenStateFlow(screenIdentifier).collectAsState()
    val screenNavigator = ScreenNavigator.Default(navigator)

    when (screenIdentifier.screen) {
        Screen.HomeScreen ->
            HomeScreen(
                screenState = state as HomeScreenState,
                eventHandler = HomeEventHandler(screenNavigator),
            )

        Screen.FavoritesScreen ->
            FavoritesScreen(
                screenState = state as FavoritesScreenState,
                eventHandler = FavoritesEventHandler(screenNavigator, events),
            )

        Screen.SectionScreen ->
            SectionScreen(
                screenState = state as SectionScreenState,
                eventHandler = SectionEventHandler(screenNavigator),
            )
        Screen.CountryDetail ->
            DetailsScreen(
                screenState = state as DetailsScreenState,
                eventHandler = DetailsEventHandler(events),
            )

        Screen.SearchScreen ->
            SearchScreen(
                screenState = state as SearchScreenState,
                eventHandler = SearchEventHandler(screenNavigator, events),
            )

        Screen.SettingsScreen ->
            SettingsScreen(
                screenState = state as SettingsScreenState,
                eventHandler = SettingsEventHandler(events),
            )

        Screen.CountriesList ->
            CountriesListScreen(
                countriesListState = state as CountriesListState,
                onListItemClick = {
                    navigate(Screen.CountryDetail, CountryDetailParams(countryCode = it.id, screenTitle = it.name)
                ) },

                onExitScreen = {

                  //  navigate(Screen.HomeScreen, null)
                    val currentScreenIdentifier = stateManager.currentScreenIdentifier

                    stateManager.removeScreen(currentScreenIdentifier)



                    navigateByLevel1(Level1Navigation.Home)


                }
            )





        else -> {}
    }

}