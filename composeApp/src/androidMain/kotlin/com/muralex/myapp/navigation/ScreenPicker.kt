package com.muralex.myapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.muralex.myapp.screens.*
import com.muralex.myapp.screens.search.SearchScreen
import com.muralex.myapp.screens.search.onSearchScreenEvent
import com.muralex.myapp.viewmodel.Navigation
import com.muralex.myapp.viewmodel.ScreenIdentifier
import com.muralex.myapp.viewmodel.ScreenParams
import com.muralex.myapp.viewmodel.screens.Level1Navigation
import com.muralex.myapp.viewmodel.screens.Screen
import com.muralex.myapp.viewmodel.screens.countriesList.CountriesListState
import com.muralex.myapp.viewmodel.screens.countrydetail.CountryDetailParams
import com.muralex.myapp.viewmodel.screens.countrydetail.DetailsScreenState
import com.muralex.myapp.viewmodel.screens.countrydetail.toggleFavorite
import com.muralex.myapp.viewmodel.screens.favorites.FavoritesScreenState
import com.muralex.myapp.viewmodel.screens.favorites.toggleFavoriteBySwipe
import com.muralex.myapp.viewmodel.screens.home.HomeScreenState
import com.muralex.myapp.viewmodel.screens.search.SearchScreenState
import com.muralex.myapp.viewmodel.screens.section.SectionParams
import com.muralex.myapp.viewmodel.screens.section.SectionScreenState
import com.muralex.myapp.viewmodel.screens.settings.SettingsScreenState
import com.muralex.myapp.viewmodel.screens.settings.setThemeModeByIndex

@Composable
fun Navigation.ScreenPicker(
    screenIdentifier: ScreenIdentifier,
    navigator: ScreenNavigator,
    navigate: (Screen, ScreenParams?) -> Unit,
    navigateByLevel1: (Level1Navigation) -> Unit,
    navigateBack: () -> Unit,
) {
    val state by stateProvider.getScreenStateFlow(screenIdentifier).collectAsState()

    when (screenIdentifier.screen) {
        Screen.HomeScreen ->
            HomeScreen(
                screenState = state as HomeScreenState,
                onListItemClick = {
                    navigate(
                        Screen.CountryDetail,
                        CountryDetailParams(countryCode = it.id, screenTitle = it.name)
                    )
                },
                onSectionClick = {
                    navigate(Screen.SectionScreen, SectionParams(it, screenTitle = it))
                },
            )

        Screen.FavoritesScreen ->
            FavoritesScreen(
                screenState = state as FavoritesScreenState,
                onListItemClick = {
                    navigate(
                        Screen.CountryDetail,
                        CountryDetailParams(countryCode = it.id, screenTitle = it.name)
                    )
                },
                toggleFavorite = { code ->
                    events.toggleFavoriteBySwipe(code)
                },
            )

        Screen.SectionScreen ->
            SectionScreen  (
                screenState = state as SectionScreenState,
                onListItemClick = {
                    navigate(
                        Screen.CountryDetail,
                        CountryDetailParams(countryCode = it.id, screenTitle = it.name)
                    )
                },
            )
        Screen.SearchScreen ->
            SearchScreen(
                screenState = state as SearchScreenState,
                navigator = navigator,
                onEvent = events::onSearchScreenEvent,
            )

        Screen.SettingsScreen ->
            SettingsScreen(
                screenState = state as SettingsScreenState,
                selectThemeMode = { events.setThemeModeByIndex(it) },
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



        Screen.CountryDetail ->
            DetailsScreen(
                screenState = state as DetailsScreenState,
                toggleFavorite = { code -> events.toggleFavorite(code)  }
            )

        else -> {}
    }

}