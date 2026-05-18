package com.muralex.myapp.screens.settings

import com.muralex.models.ThemeMode
import com.muralex.myapp.viewmodel.Events
import com.muralex.myapp.viewmodel.screens.settings.saveThemeMode

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
}

