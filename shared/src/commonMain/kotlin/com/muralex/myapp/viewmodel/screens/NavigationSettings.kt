package com.muralex.myapp.viewmodel.screens

import com.muralex.myapp.viewmodel.ScreenIdentifier
import com.muralex.myapp.viewmodel.screens.Screen.*
import com.muralex.myapp.viewmodel.screens.countriesList.CountriesListParams
import com.muralex.myapp.viewmodel.screens.countriesList.CountriesListType

// CONFIGURATION SETTINGS

object navigationSettings {
    val homeScreen = Level1Navigation.AllCountries // the start screen should be specified here
    val saveLastLevel1Screen = true
    val alwaysQuitOnHomeScreen = true
}


// LEVEL 1 NAVIGATION OF THE APP

enum class Level1Navigation(val screenIdentifier: ScreenIdentifier, val rememberVerticalStack: Boolean = false) {
    AllCountries( ScreenIdentifier.get(CountriesList, CountriesListParams(listType = CountriesListType.ALL)), true),
    FavoriteCountries( ScreenIdentifier.get(CountriesList,
        CountriesListParams(listType = CountriesListType.FAVORITES)
    ), true),
}