package com.muralex.myapp.viewmodel.screens.settings


import com.muralex.core.common.result.DataResult
import com.muralex.core.common.result.isSuccess
import com.muralex.data.repository.functions.getFavoriteSwipeEnabled
import com.muralex.data.repository.functions.getThemeMode
import com.muralex.data.repository.functions.saveThemeMode
import com.muralex.data.repository.functions.setFavoriteSwipeEnabled
import com.muralex.data.repository.functions.updateCountriesListData
import com.muralex.models.ThemeMode
import com.muralex.myapp.viewmodel.Events
import com.muralex.myapp.viewmodel.resources.FormattedText
import com.muralex.myapp.viewmodel.resources.SharedRes
import com.muralex.myapp.viewmodel.screens.Level1Navigation
import com.muralex.myapp.viewmodel.utils.toFormattedDate

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
                    formattedSummary = FormattedText.SimpleText.of(SharedRes.Strings.settings_sync_in_progress)
                )
            }
        )
    }

    val result = dataRepository.updateCountriesListData(forceUpdate = true)
    val lastUpdate = dataRepository.localSettings.listCacheTimestamp
    val timeLabel = lastUpdate.toFormattedDate()

    if (result.isSuccess()) {
        Level1Navigation.Home.screenIdentifier.getScreenInitSettings(stateManager).callOnInit(stateManager)
        Level1Navigation.Favorites.screenIdentifier.getScreenInitSettings(stateManager).callOnInit(stateManager)
    }

    val formattedSummary = when (result) {
        is DataResult.Success -> buildSyncResultSummary(true, timeLabel)
        is DataResult.Error -> buildSyncResultSummary(false, timeLabel)
    }

    stateManager.updateScreen(SettingsScreenState::class) {
        it.copy(
            categories = it.categories.updateSetting("sync_data") { setting ->
                (setting as Setting.Action).copy(
                    formattedSummary = formattedSummary
                )
            }
        )
    }
}
