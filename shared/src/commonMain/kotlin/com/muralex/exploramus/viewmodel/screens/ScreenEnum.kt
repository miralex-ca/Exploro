package com.muralex.exploramus.viewmodel.screens

import com.muralex.exploramus.viewmodel.ScreenIdentifier
import com.muralex.exploramus.viewmodel.StateManager
import com.muralex.exploramus.viewmodel.screens.countrydetail.initCountryDetail
import com.muralex.exploramus.viewmodel.screens.favorites.initFavoritesScreen
import com.muralex.exploramus.viewmodel.screens.home.initHomeScreen
import com.muralex.exploramus.viewmodel.screens.search.initSearchScreen
import com.muralex.exploramus.viewmodel.screens.section.initSectionScreen
import com.muralex.exploramus.viewmodel.screens.settings.initSettingsScreen

enum class Screen(
    val asString: String,
    val navigationLevel: Int = 1,
    val initSettings: StateManager.(ScreenIdentifier) -> ScreenInitSettings,
) {
    HomeScreen("home", 1, {
        initHomeScreen()
    }),

    CountryDetail("country", 3, {
        initCountryDetail(it.params())
    }),

    SectionScreen("section", 2, {
        initSectionScreen(params = it.params())
    }),

    SearchScreen("search", 2, {
        initSearchScreen()
    }),

    SettingsScreen("settings", 2, {
        initSettingsScreen()
    }),

    FavoritesScreen("favorites", 1, {
        initFavoritesScreen()
    }),
}