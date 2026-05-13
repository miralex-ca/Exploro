package com.muralex.myapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.muralex.myapp.screens.CountriesListScreen
import com.muralex.myapp.screens.CountryDetailScreen
import com.muralex.myapp.screens.FavoritesScreen
import com.muralex.myapp.screens.HomeScreen
import com.muralex.myapp.viewmodel.Navigation
import com.muralex.myapp.viewmodel.ScreenIdentifier
import com.muralex.myapp.viewmodel.ScreenParams
import com.muralex.myapp.viewmodel.screens.Level1Navigation
import com.muralex.myapp.viewmodel.screens.Screen
import com.muralex.myapp.viewmodel.screens.countriesList.CountriesListState
import com.muralex.myapp.viewmodel.screens.countrydetail.CountryDetailParams
import com.muralex.myapp.viewmodel.screens.countrydetail.CountryDetailState
import com.muralex.myapp.viewmodel.screens.favorites.FavoritesScreenState
import com.muralex.myapp.viewmodel.screens.home.HomeScreenState

@Composable
fun Navigation.ScreenPicker(
    screenIdentifier: ScreenIdentifier,
    navigate: (Screen, ScreenParams?) -> Unit,
    navigateByLevel1: (Level1Navigation) -> Unit,
) {
    val state by stateProvider.getScreenStateFlow(screenIdentifier).collectAsState()

    when (screenIdentifier.screen) {
        Screen.HomeScreen ->
            HomeScreen(
                screenState = state as HomeScreenState,
                onListItemClick = {
                    navigate(
                        Screen.CountryDetail,
                        CountryDetailParams(countryName = it.name, countryCode = it.id)
                    )
                },
            )

        Screen.FavoritesScreen ->
            FavoritesScreen(
                screenState = state as FavoritesScreenState,
                onListItemClick = {
                    navigate(
                        Screen.CountryDetail,
                        CountryDetailParams(countryName = it.name, countryCode = it.id)
                    )
                },
            )

        Screen.CountriesList ->
            CountriesListScreen(
                countriesListState = state as CountriesListState,
                onListItemClick = {
                    navigate(Screen.CountryDetail, CountryDetailParams(countryName = it.name, countryCode = it.id)
                ) },

                onExitScreen = {

                  //  navigate(Screen.HomeScreen, null)
                    val currentScreenIdentifier = stateManager.currentScreenIdentifier

                    stateManager.removeScreen(currentScreenIdentifier)



                    navigateByLevel1(Level1Navigation.Home)


                }
            )



        Screen.CountryDetail ->
            CountryDetailScreen(
                countryDetailState = state as CountryDetailState
            )

        else -> {}
    }

}