package com.muralex.myapp.screens.settings

import com.muralex.models.ThemeMode
import com.muralex.myapp.viewmodel.Events
import com.muralex.myapp.viewmodel.screens.settings.builder.SettingAction
import com.muralex.myapp.viewmodel.screens.settings.saveThemeMode
import com.muralex.myapp.viewmodel.screens.settings.setFavoriteSwipeEnabled
import com.muralex.myapp.viewmodel.screens.settings.syncDataFromSettings

sealed class SettingsUiEvent

class SettingsEventHandler(
    val events: Events
) {
    fun onEvent(event: SettingsUiEvent) {}

    fun onSettingAction(action: SettingAction) {
        when (action) {
            is SettingAction.SetFavoriteSwipe -> {
                events.setFavoriteSwipeEnabled(action.enabled)
            }

            is SettingAction.SetThemeMode -> {
                events.saveThemeMode(ThemeMode.byName(action.value))
            }

            SettingAction.SyncData -> {
                events.syncDataFromSettings()
            }
        }
    }
}

