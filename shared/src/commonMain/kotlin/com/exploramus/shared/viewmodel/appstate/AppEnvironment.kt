package com.exploramus.shared.viewmodel.appstate

import com.exploramus.data.repository.functions.getFavoriteSwipeEnabled
import com.exploramus.data.repository.functions.getThemeMode
import com.exploramus.shared.viewmodel.core.StateManager
import com.exploramus.core.models.ThemeMode

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

