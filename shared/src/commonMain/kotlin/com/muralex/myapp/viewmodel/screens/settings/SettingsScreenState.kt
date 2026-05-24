package com.muralex.myapp.viewmodel.screens.settings

import com.muralex.data.repository.Repository
import com.muralex.models.ThemeMode
import com.muralex.myapp.viewmodel.ScreenState
import com.muralex.myapp.viewmodel.resources.SharedRes
import com.muralex.myapp.viewmodel.resources.StringRef

data class SettingsScreenState(
    val isLoading: Boolean = false,
    val settings: List<Setting> = emptyList(),
    val savedThemeMode: ThemeMode = ThemeMode.DEFAULT,
    private val settingsBuilder: SettingsBuilder? = null,
) : ScreenState {

    fun getUpdatedSettings(): List<Setting> {
        return settingsBuilder?.build() ?: settings
    }
}


sealed class Setting {
    abstract val key: String
    abstract val title: StringRef
    abstract val summary: StringRef?

    data class Switch(
        override val key: String,
        override val title: StringRef,
        override val summary: StringRef? = null,
        val summaryOn: StringRef? = null,
        val summaryOff: StringRef? = null,
        val defaultValue: Boolean = false,
        val value: Boolean = false,
        val onToggle: () -> SettingAction,
    ) : Setting()

    data class Options(
        override val key: String,
        override val title: StringRef,
        override val summary: StringRef? = null,
        val options: List<SettingOption>,
        val selectedValue: String = "",
        val dialogTitle: StringRef? = null,
        val onSelect: (String) -> SettingAction
    ) : Setting()

    data class Action(
        override val key: String,
        override val title: StringRef,
        override val summary: StringRef? = null,
        val dialogTitle: StringRef? = null,
        val dialogMessage: StringRef? = null,
        val onClick: () -> SettingAction
    ) : Setting()
}

data class SettingOption(
    val value: String,
    val label: StringRef
)

sealed class SettingAction {
    data class SetThemeMode(val value: String) : SettingAction()
    data class SetFavoriteSwipe(val enabled: Boolean) : SettingAction()
    data object SyncData: SettingAction()
}

class SettingsBuilder(repository: Repository) {
    val localSettings = repository.localSettings

    val settings = listOf(
        addThemeMode(),
        addFavoriteSwipe(),
        addSync(),
    )

    fun build(): List<Setting> {
        return settings
    }

    private fun addThemeMode(): Setting {
        val themeModeName = ThemeMode.fromId(localSettings.themeModeId).name

        return Setting.Options(
            key = "theme_mode",
            title = SharedRes.Strings.settings_theme_title,
            summary = SharedRes.Strings.settings_theme_summary,
            options = listOf(
                SettingOption(ThemeMode.LIGHT.name, SharedRes.Strings.settings_theme_option_light),
                SettingOption(ThemeMode.DARK.name, SharedRes.Strings.settings_theme_option_dark),
                SettingOption(ThemeMode.SYSTEM.name, SharedRes.Strings.settings_theme_option_system),
            ),
            selectedValue = themeModeName,
            onSelect = { SettingAction.SetThemeMode(it) }
        )
    }

    private fun addFavoriteSwipe() : Setting {
        val isEnabled = localSettings.favoriteSwipeEnabled
        return Setting.Switch(
            key = "favorite_swipe",
            title = SharedRes.Strings.settings_favorite_swipe_title,
            summaryOn = SharedRes.Strings.settings_favorite_swipe_summaryOn,
            summaryOff = SharedRes.Strings.settings_favorite_swipe_summaryOff,
            defaultValue = false,
            value = isEnabled,
            onToggle = { SettingAction.SetFavoriteSwipe(!isEnabled) }
        )
    }

    private fun addSync() : Setting {
        return Setting.Action(
            key = "sync_data",
            title = SharedRes.Strings.settings_sync_title,
            summary = SharedRes.Strings.settings_sync_summary,
            onClick = { SettingAction.SyncData }
        )
    }
}





