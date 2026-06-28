package com.exploramus.shared.viewmodel.screens.settings


import com.exploramus.core.common.result.isSuccess
import com.exploramus.core.models.ThemeMode
import com.exploramus.data.repository.functions.getFavoriteSwipeEnabled
import com.exploramus.data.repository.functions.getThemeMode
import com.exploramus.data.repository.functions.saveThemeMode
import com.exploramus.data.repository.functions.setFavoriteSwipeEnabled
import com.exploramus.data.repository.functions.updateCountriesListData
import com.exploramus.shared.resources.FormattedText
import com.exploramus.shared.resources.SharedRes
import com.exploramus.shared.viewmodel.core.Events
import com.exploramus.shared.viewmodel.core.StateManager
import com.exploramus.shared.viewmodel.screens.Level1Navigation
import com.exploramus.shared.viewmodel.screens.settings.builder.DataSettingsCategory
import com.exploramus.shared.viewmodel.screens.settings.builder.InterfaceSettingsCategory
import com.exploramus.shared.viewmodel.screens.settings.builder.Setting
import com.exploramus.shared.viewmodel.screens.settings.builder.updateSetting
import com.exploramus.shared.viewmodel.utils.toFormattedDate

fun Events.updateSetting(key: String, value: String) {
    when (key) {
        InterfaceSettingsCategory.THEME_SETTING_ID -> {
            updateThemeMode(name = value)
        }
    }
}

fun Events.updateSetting(key: String, value: Boolean) {
    when (key) {
        InterfaceSettingsCategory.FAVORITE_SWIPE_SETTING_ID -> {
            updateFavoriteSwipeEnabled(enabled = value)
        }
    }
}

fun Events.triggerSettingAction(key: String) {
    when (key) {
        DataSettingsCategory.SYNC_SETTING_ID -> {
            syncDataFromSettings()
        }
    }
}

fun Events.updateThemeMode(name: String) = appCoroutine {
    val themeMode = ThemeMode.byName(name)
    dataRepository.saveThemeMode(themeMode)
    val current = dataRepository.getThemeMode()

    stateManager.updateAppEnvironment(
        state = stateManager.appEnvironment.value.copy(
            themeMode = current
        )
    )

    stateManager.settingsManager.updateSettingsState()
    stateManager.refreshSettingsScreen()
}

fun Events.updateFavoriteSwipeEnabled(enabled: Boolean) = appCoroutine {
    dataRepository.setFavoriteSwipeEnabled(enabled)
    val isFavoriteSwipeEnabled = dataRepository.getFavoriteSwipeEnabled()

    stateManager.updateAppEnvironment(
        state = stateManager.appEnvironment.value.copy(
            favoriteSwipeEnabled = isFavoriteSwipeEnabled
        )
    )
    stateManager.settingsManager.updateSettingsState()
    stateManager.refreshSettingsScreen()
}


fun Events.syncDataFromSettings() = screenCoroutine {
    val progressSummary = FormattedText.SimpleText.of(SharedRes.Strings.settings_sync_in_progress)
    stateManager.updateSyncSummary(progressSummary)

    val result = dataRepository.updateCountriesListData(forceUpdate = true)

    if (result.isSuccess()) {
        stateManager.reinitLevel1Screens()
    }

    val timeLabel = dataRepository.localSettings.dataCacheTimestamp.toFormattedDate()
    val synResultSummary = DataSettingsCategory.buildSyncResultSummary(result, timeLabel)
    stateManager.updateSyncSummary(synResultSummary)
}

private fun StateManager.updateSyncSummary(summary: FormattedText?) {
    val settings = settingsManager.getCategories()
    val settingKey = DataSettingsCategory.SYNC_SETTING_ID

    val updatedSettings = settings.updateSetting(settingKey) { setting ->
        (setting as Setting.Action).copy(formattedSummary = summary)
    }

    settingsManager.setSettingsState(updatedSettings)
    refreshSettingsScreen()
}

fun StateManager.refreshSettingsScreen() {
     updateScreen(SettingsScreenState::class) {
        it.copy(categories = settingsManager.settings.value)
    }
}

private suspend fun StateManager.reinitLevel1Screens() {
    Level1Navigation.Home.screenIdentifier.getScreenInitSettings(this).callOnInit(this)
    Level1Navigation.Favorites.screenIdentifier.getScreenInitSettings(this).callOnInit(this)
}

// used in ios settings view
fun Events.updateSettingsState() = appCoroutine {
    stateManager.settingsManager.updateSettingsState()
}

