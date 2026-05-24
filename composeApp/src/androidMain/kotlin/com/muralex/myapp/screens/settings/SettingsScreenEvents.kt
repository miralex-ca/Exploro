package com.muralex.myapp.screens.settings

import com.muralex.models.ThemeMode
import com.muralex.myapp.viewmodel.Events
import com.muralex.myapp.viewmodel.screens.settings.SettingAction
import com.muralex.myapp.viewmodel.screens.settings.saveThemeMode
import com.muralex.myapp.viewmodel.screens.settings.setFavoriteSwipeEnabled

sealed class SettingsUiEvent {
    data class OnThemeSelected(val mode: ThemeMode) : SettingsUiEvent()
}

class SettingsEventHandler(
    val events: Events
) {
    fun onEvent(event: SettingsUiEvent) {
        when (event) {
            is SettingsUiEvent.OnThemeSelected -> events.saveThemeMode(event.mode)
        }
    }

    fun onSettingAction(action: SettingAction) {
        when (action) {
            is SettingAction.SetFavoriteSwipe -> {
                events.setFavoriteSwipeEnabled(action.enabled)
            }

            is SettingAction.SetThemeMode -> {
                events.saveThemeMode(ThemeMode.byName(action.value))
            }
        }
    }
}

