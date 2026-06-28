package com.exploramus.app.composables.screens.settings

import com.exploramus.shared.viewmodel.core.Events
import com.exploramus.shared.viewmodel.screens.settings.triggerSettingAction
import com.exploramus.shared.viewmodel.screens.settings.updateSetting

sealed class SettingsUiEvent

sealed class SettingsUiAction {
    data class Toggle(val key: String, val value: Boolean) : SettingsUiAction()
    data class Select(val key: String, val value: String) : SettingsUiAction()
    data class Trigger(val key: String) : SettingsUiAction()
}

class SettingsEventHandler(
    val events: Events
) {
    fun onEvent(event: SettingsUiEvent) {}

    fun onSettingAction(action: SettingsUiAction) {
        when (action) {
            is SettingsUiAction.Toggle -> {
                events.updateSetting(action.key, action.value)
            }

            is SettingsUiAction.Select -> {
                events.updateSetting(action.key, action.value)
            }

            is SettingsUiAction.Trigger -> {
                events.triggerSettingAction(action.key)
            }
        }
    }
}

