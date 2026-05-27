package com.muralex.exploramus.viewmodel.appenvironment

import com.muralex.data.repository.functions.getFavoriteSwipeEnabled
import com.muralex.data.repository.functions.getThemeMode
import com.muralex.models.ThemeMode
import com.muralex.exploramus.viewmodel.StateManager

data class AppEnvironment(
    val themeMode: ThemeMode = ThemeMode.DEFAULT,
    val favoriteSwipeEnabled: Boolean = false
)

fun StateManager.prepareAppEnvironment() {
    val appEnvironment = AppEnvironment(
        themeMode =  dataRepository.getThemeMode(),
        favoriteSwipeEnabled = dataRepository.getFavoriteSwipeEnabled()
    )
    updateAppEnvironment(appEnvironment)
}

