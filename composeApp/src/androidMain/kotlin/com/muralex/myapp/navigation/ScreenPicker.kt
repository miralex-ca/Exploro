package com.muralex.myapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.muralex.myapp.screens.CountriesListScreen
import com.muralex.myapp.screens.CountryDetailScreen
import com.muralex.myapp.viewmodel.Navigation
import com.muralex.myapp.viewmodel.ScreenIdentifier
import com.muralex.myapp.viewmodel.ScreenParams
import com.muralex.myapp.viewmodel.screens.Screen
import com.muralex.myapp.viewmodel.screens.countriesList.CountriesListState
import com.muralex.myapp.viewmodel.screens.countriesList.selectFavorite
import com.muralex.myapp.viewmodel.screens.countrydetail.CountryDetailParams
import com.muralex.myapp.viewmodel.screens.countrydetail.CountryDetailState

@Composable
fun Navigation.ScreenPicker(
    screenIdentifier: ScreenIdentifier,
    navigate: (Screen, ScreenParams?) -> Unit
) {
    val state by stateProvider.getScreenStateFlow(screenIdentifier).collectAsState()

    when (screenIdentifier.screen) {
        Screen.CountriesList ->
            CountriesListScreen(
                countriesListState = state as CountriesListState,
                onListItemClick = {
                    navigate(Screen.CountryDetail, CountryDetailParams(countryName = it.name, countryCode = it.id)
                ) },
                onFavoriteIconClick = { events.selectFavorite(countryName = it) },
            )

        Screen.CountryDetail ->
            CountryDetailScreen(
                countryDetailState = state as CountryDetailState
            )

        else -> {}
    }

}