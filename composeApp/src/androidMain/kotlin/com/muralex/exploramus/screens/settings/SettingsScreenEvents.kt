package com.muralex.exploramus.screens.settings

import com.muralex.models.ThemeMode
import com.muralex.exploramus.viewmodel.Events
import com.muralex.exploramus.viewmodel.screens.settings.builder.SettingAction
import com.muralex.exploramus.viewmodel.screens.settings.saveThemeMode
import com.muralex.exploramus.viewmodel.screens.settings.setFavoriteSwipeEnabled
import com.muralex.exploramus.viewmodel.screens.settings.syncDataFromSettings

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

