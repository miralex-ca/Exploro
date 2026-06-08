package com.muralex.exploramus.screens.settings

import com.muralex.exploramus.viewmodel.core.Events
import com.muralex.exploramus.viewmodel.screens.settings.builder.SettingAction
import com.muralex.exploramus.viewmodel.screens.settings.saveThemeMode
import com.muralex.exploramus.viewmodel.screens.settings.setFavoriteSwipeEnabled
import com.muralex.exploramus.viewmodel.screens.settings.syncDataFromSettings
import com.muralex.models.ThemeMode

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
                events.saveThemeMode(name = action.value)
            }

            SettingAction.SyncData -> {
                events.syncDataFromSettings()
            }
        }
    }
}

