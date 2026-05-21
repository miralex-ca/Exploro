package com.muralex.myapp.viewmodel.screens

import com.muralex.myapp.viewmodel.ScreenIdentifier
import com.muralex.myapp.viewmodel.screens.Screen.HomeScreen

// CONFIGURATION SETTINGS

object navigationSettings {
    val homeScreen = Level1Navigation.Home // the start screen should be specified here
    val saveLastLevel1Screen = false
    val alwaysQuitOnHomeScreen = true
}

// LEVEL 1 NAVIGATION OF THE APP

enum class Level1Navigation(
    val screenIdentifier: ScreenIdentifier,
    val rememberVerticalStack: Boolean = false
) {
    Home(ScreenIdentifier.get(HomeScreen, null), true),
    Favorites(ScreenIdentifier.get(Screen.FavoritesScreen, null), true),
}