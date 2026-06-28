package com.exploramus.app.composables.screens.settings

import com.exploramus.shared.viewmodel.core.Events
import com.exploramus.shared.viewmodel.screens.settings.builder.SettingAction
import com.exploramus.shared.viewmodel.screens.settings.saveThemeMode
import com.exploramus.shared.viewmodel.screens.settings.setFavoriteSwipeEnabled
import com.exploramus.shared.viewmodel.screens.settings.syncDataFromSettings

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

