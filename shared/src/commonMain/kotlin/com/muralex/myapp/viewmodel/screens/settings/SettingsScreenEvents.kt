package com.muralex.myapp.viewmodel.screens.settings


import com.muralex.data.repository.functions.getFavoriteSwipeEnabled
import com.muralex.data.repository.functions.getThemeMode
import com.muralex.data.repository.functions.saveThemeMode
import com.muralex.data.repository.functions.setFavoriteSwipeEnabled
import com.muralex.models.ThemeMode
import com.muralex.myapp.viewmodel.Events

fun Events.setFavoriteSwipeEnabled(enabled: Boolean) = screenCoroutine {
    dataRepository.setFavoriteSwipeEnabled(enabled)
    val isFavoriteSwipeEnabled = dataRepository.getFavoriteSwipeEnabled()

    stateManager.updateAppEnvironment(
        state = stateManager.appEnvironment.value.copy(
            favoriteSwipeEnabled = isFavoriteSwipeEnabled
        )
    )

    val builder = SettingsBuilder(repository = dataRepository)


    stateManager.updateScreen(SettingsScreenState::class) {
        it.copy(
            categories = builder.buildCategories(),
            settings = builder.build().also { println("Settings $it") }
        )
    }
}

fun Events.saveThemeMode(themeMode: ThemeMode) = screenCoroutine {
    dataRepository.saveThemeMode(themeMode)
    val current = dataRepository.getThemeMode()

    stateManager.updateAppEnvironment(
        state = stateManager.appEnvironment.value.copy(
            themeMode = current
        )
    )

    val builder = SettingsBuilder(repository = dataRepository)

    stateManager.updateScreen(SettingsScreenState::class) {
        it.copy(
            categories = builder.buildCategories(),
            settings = builder.build()
        )
    }


}