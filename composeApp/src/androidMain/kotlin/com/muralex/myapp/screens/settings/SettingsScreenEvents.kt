package com.muralex.myapp.screens.settings

import com.muralex.myapp.viewmodel.Events
import com.muralex.myapp.viewmodel.screens.settings.setThemeModeByIndex

sealed class SettingsUiEvent {
    data class OnThemeSelected(val themeIndex: Int) : SettingsUiEvent()
}

class SettingsEventHandler(
    val events: Events
) {
    fun onEvent(event: SettingsUiEvent) {
        when (event) {
            is SettingsUiEvent.OnThemeSelected -> events.setThemeModeByIndex(event.themeIndex)
        }
    }
}

