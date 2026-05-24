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
        val entries: List<StringRef>,
        val entryValues: List<String>,
        val selectedValue: String = "",
        val dialogTitle: StringRef? = null,
        val onSelect: (String) -> SettingAction
    ) : Setting()

    data class Action(
        override val key: String,
        override val title: StringRef,
        override val summary: StringRef? = null,
        val onClick: () -> SettingAction
    ) : Setting()
}

sealed class SettingAction {
    data class SetThemeMode(val value: String) : SettingAction()
    data class SetFavoriteSwipe(val enabled: Boolean) : SettingAction()
}

class SettingsBuilder(repository: Repository) {

    val localSettings = repository.localSettings

    val settings = listOf(
        addThemeMode(),
        addFavoriteSwipe(),
    )

    fun build(): List<Setting> {
        return settings
    }

    private fun addThemeMode() : Setting {
        val themeModeName = ThemeMode.fromId(localSettings.themeModeId).name

        return Setting.Options(
            key = "theme_mode",
            title = SharedRes.Strings.settings_theme_title,
            summary = SharedRes.Strings.settings_theme_summary,
            entries = listOf(
                SharedRes.Strings.settings_theme_option_dark,
                SharedRes.Strings.settings_theme_option_light,
                SharedRes.Strings.settings_theme_option_system,
            ),
            entryValues = listOf(
                ThemeMode.LIGHT.name,
                ThemeMode.DARK.name,
                ThemeMode.SYSTEM.name,
            ),
            selectedValue = themeModeName,
            onSelect =  { theme -> SettingAction.SetThemeMode(theme) }
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
}



