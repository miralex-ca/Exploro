package com.muralex.myapp.viewmodel.screens.settings


import com.muralex.core.common.result.DataResult
import com.muralex.data.repository.functions.getFavoriteSwipeEnabled
import com.muralex.data.repository.functions.getThemeMode
import com.muralex.data.repository.functions.saveThemeMode
import com.muralex.data.repository.functions.setFavoriteSwipeEnabled
import com.muralex.data.repository.functions.updateCountriesListData
import com.muralex.models.ThemeMode
import com.muralex.myapp.viewmodel.Events
import com.muralex.myapp.viewmodel.resources.SharedRes

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

fun Events.syncDataFromSettings() = screenCoroutine {
    stateManager.updateScreen(SettingsScreenState::class) {
        it.copy(
            categories = it.categories.updateSetting("sync_data") { setting ->
                (setting as Setting.Action).copy(
                    summary = SharedRes.Strings.settings_sync_in_progress
                )
            }
        )
    }

  //  delay(2000)

    val result = dataRepository.updateCountriesListData(forceUpdate = true)

    stateManager.updateScreen(SettingsScreenState::class) {
        it.copy(
            categories = it.categories.updateSetting("sync_data") { setting ->
                (setting as Setting.Action).copy(
                    summary = when (result) {
                        is DataResult.Success -> SharedRes.Strings.settings_sync_success
                        is DataResult.Error -> SharedRes.Strings.settings_sync_failed
                    }
                )
            }
        )
    }
}
