package com.exploramus.shared.viewmodel.screens.settings


import com.exploramus.core.common.result.isSuccess
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
import com.exploramus.shared.viewmodel.screens.settings.builder.SettingsCategory
import com.exploramus.shared.viewmodel.screens.settings.builder.updateSetting
import com.exploramus.shared.viewmodel.utils.toFormattedDate
import com.exploramus.core.models.ThemeMode

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
    updateSettingsSyncSummary(summary)

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
        it.copy(categories = settingsManager.getCategories())
    }
}

private suspend fun StateManager.reinitLevel1Screens() {
    Level1Navigation.Home.screenIdentifier.getScreenInitSettings(this).callOnInit(this)
    Level1Navigation.Favorites.screenIdentifier.getScreenInitSettings(this).callOnInit(this)
}


fun Events.buildSettingsCategories(callback: (List<SettingsCategory>) -> Unit ) = appCoroutine {
    val categories = stateManager.settingsManager.getCategories()
    callback.invoke(categories)
}



fun Events.updateSettingsState() = appCoroutine {
    stateManager.settingsManager.updateSettingsState()
}

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
}

private fun StateManager.updateSettingsSyncSummary(summary: FormattedText?) {
    val settings = settingsManager.getCategories()

    val updatedSettings = settings.updateSetting(DataSettingsCategory.SYNC_SETTING_ID) { setting ->
        (setting as Setting.Action).copy(formattedSummary = summary)
    }

    settingsManager.setSettingsState(updatedSettings)
}
