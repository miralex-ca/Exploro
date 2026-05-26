package com.muralex.myapp.viewmodel.screens.settings


import com.muralex.core.common.result.isSuccess
import com.muralex.data.repository.functions.getFavoriteSwipeEnabled
import com.muralex.data.repository.functions.getThemeMode
import com.muralex.data.repository.functions.saveThemeMode
import com.muralex.data.repository.functions.setFavoriteSwipeEnabled
import com.muralex.data.repository.functions.updateCountriesListData
import com.muralex.models.ThemeMode
import com.muralex.myapp.viewmodel.Events
import com.muralex.myapp.viewmodel.StateManager
import com.muralex.myapp.viewmodel.resources.FormattedText
import com.muralex.myapp.viewmodel.resources.SharedRes
import com.muralex.myapp.viewmodel.screens.Level1Navigation
import com.muralex.myapp.viewmodel.screens.settings.builder.DataSettingsCategory
import com.muralex.myapp.viewmodel.screens.settings.builder.Setting
import com.muralex.myapp.viewmodel.screens.settings.builder.updateSetting
import com.muralex.myapp.viewmodel.utils.toFormattedDate

fun Events.setFavoriteSwipeEnabled(enabled: Boolean) = screenCoroutine {
    dataRepository.setFavoriteSwipeEnabled(enabled)
    val isFavoriteSwipeEnabled = dataRepository.getFavoriteSwipeEnabled()

    stateManager.updateAppEnvironment(
        state = stateManager.appEnvironment.value.copy(
            favoriteSwipeEnabled = isFavoriteSwipeEnabled
        )
    )
    stateManager.rebuildSettings()
}

fun Events.saveThemeMode(themeMode: ThemeMode) = screenCoroutine {
    dataRepository.saveThemeMode(themeMode)
    val current = dataRepository.getThemeMode()

    stateManager.updateAppEnvironment(
        state = stateManager.appEnvironment.value.copy(
            themeMode = current
        )
    )
    stateManager.rebuildSettings()
}

fun Events.syncDataFromSettings() = screenCoroutine {
    stateManager.updateSyncSummary(
        FormattedText.SimpleText.of(SharedRes.Strings.settings_sync_in_progress)
    )

    val result = dataRepository.updateCountriesListData(forceUpdate = true)

    if (result.isSuccess()) {
        stateManager.reinitLevel1Screens()
    }
    val timeLabel = dataRepository.localSettings.listCacheTimestamp.toFormattedDate()

    stateManager.updateSyncSummary(
        DataSettingsCategory.buildSyncResultSummary(result, timeLabel)
    )
}

private fun StateManager.updateSyncSummary(summary: FormattedText?) {
    updateScreen(SettingsScreenState::class) {
        it.copy(
            categories = it.categories.updateSetting(DataSettingsCategory.SYNC_SETTING_ID) { setting ->
                (setting as Setting.Action).copy(formattedSummary = summary)
            }
        )
    }
}

private fun StateManager.rebuildSettings() {
    updateScreen(SettingsScreenState::class) {
        it.copy(categories = settingsBuilder.buildCategories())
    }
}

private suspend fun StateManager.reinitLevel1Screens() {
    Level1Navigation.Home.screenIdentifier.getScreenInitSettings(this).callOnInit(this)
    Level1Navigation.Favorites.screenIdentifier.getScreenInitSettings(this).callOnInit(this)
}
