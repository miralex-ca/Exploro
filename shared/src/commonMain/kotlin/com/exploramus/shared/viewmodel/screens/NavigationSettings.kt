package com.exploramus.shared.viewmodel.screens

import com.exploramus.shared.viewmodel.core.ScreenIdentifier
import com.exploramus.shared.viewmodel.screens.Screen.HomeScreen

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
    Lv1Settings(ScreenIdentifier.get(Screen.Lv1SettingsScreen, null), true),
    Lv1Search(ScreenIdentifier.get(Screen.Lv1SearchScreen, null), true),
}