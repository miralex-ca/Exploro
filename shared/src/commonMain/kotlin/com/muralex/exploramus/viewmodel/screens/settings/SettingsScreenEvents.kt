package com.muralex.exploramus.viewmodel.screens.settings


import com.muralex.core.common.result.isSuccess
import com.muralex.data.repository.functions.getFavoriteSwipeEnabled
import com.muralex.data.repository.functions.getThemeMode
import com.muralex.data.repository.functions.saveThemeMode
import com.muralex.data.repository.functions.setFavoriteSwipeEnabled
import com.muralex.data.repository.functions.updateCountriesListData
import com.muralex.exploramus.resources.FormattedText
import com.muralex.exploramus.resources.SharedRes
import com.muralex.exploramus.viewmodel.core.Events
import com.muralex.exploramus.viewmodel.core.StateManager
import com.muralex.exploramus.viewmodel.screens.Level1Navigation
import com.muralex.exploramus.viewmodel.screens.settings.builder.DataSettingsCategory
import com.muralex.exploramus.viewmodel.screens.settings.builder.Setting
import com.muralex.exploramus.viewmodel.screens.settings.builder.updateSetting
import com.muralex.exploramus.viewmodel.utils.toFormattedDate
import com.muralex.models.ThemeMode

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

fun Events.saveThemeMode(name: String) = screenCoroutine {
    val themeMode = ThemeMode.byName(name)
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
