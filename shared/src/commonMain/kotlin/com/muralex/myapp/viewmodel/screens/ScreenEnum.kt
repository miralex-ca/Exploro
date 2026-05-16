package com.muralex.myapp.viewmodel.screens

import com.muralex.myapp.viewmodel.ScreenIdentifier
import com.muralex.myapp.viewmodel.StateManager
import com.muralex.myapp.viewmodel.screens.countriesList.initCountriesList
import com.muralex.myapp.viewmodel.screens.countrydetail.initCountryDetail
import com.muralex.myapp.viewmodel.screens.favorites.initFavoritesScreen
import com.muralex.myapp.viewmodel.screens.home.initHomeScreen
import com.muralex.myapp.viewmodel.screens.search.initSearchScreen
import com.muralex.myapp.viewmodel.screens.section.initSectionScreen

enum class Screen(
    val asString: String,
    val navigationLevel : Int = 1,
    val initSettings: StateManager.(ScreenIdentifier) -> ScreenInitSettings,
) {
    HomeScreen("home", 1, {
        initHomeScreen()
    }),

    FavoritesScreen("favorites", 1, {
        initFavoritesScreen()
    }),

    SectionScreen("section", 2, {
        initSectionScreen(params = it.params())
    }),

    CountriesList("countrieslist", 1, {
        initCountriesList()
    }),

    SearchScreen("search", 2, {
        initSearchScreen()
    }),

    CountryDetail("country", 3, {
        initCountryDetail(it.params())
    }),




}