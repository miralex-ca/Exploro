package com.muralex.myapp.viewmodel.screens.settings

import com.muralex.data.repository.Repository
import com.muralex.models.ThemeMode
import com.muralex.myapp.viewmodel.ScreenState
import com.muralex.myapp.viewmodel.resources.SharedRes
import com.muralex.myapp.viewmodel.resources.StringRef
import com.muralex.myapp.viewmodel.resources.StringRefWithArgs

data class SettingsScreenState(
    val isLoading: Boolean = false,
    val settings: List<Setting> = emptyList(),
    val categories: List<SettingsCategory> = emptyList(),
    val savedThemeMode: ThemeMode = ThemeMode.DEFAULT,
    private val settingsBuilder: SettingsBuilder? = null,
) : ScreenState {

    fun getUpdatedSettings(): List<Setting> {
        return settingsBuilder?.build() ?: settings
    }
}


fun List<SettingsCategory>.updateSetting(key: String, update: (Setting) -> Setting): List<SettingsCategory> {
    return map { category ->
        category.copy(
            settings = category.settings.map { setting ->
                if (setting.key == key) update(setting) else setting
            }
        )
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
        val formattedSummary: StringRefWithArgs? = null,
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

    data class Info(
        override val key: String,
        override val title: StringRef,
        override val summary: StringRef? = null,
        val info: String? = null,
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

class SettingsBuilder(private val repository: Repository) {
    val localSettings = repository.localSettings

    val settings = listOf(
        addThemeMode(),
        addFavoriteSwipe(),
        addSync(),
    )

    fun build(): List<Setting> {
        return settings
    }

    fun buildCategories(): List<SettingsCategory> = listOf(
        SettingsCategory(
            id = "interface",
            title = SharedRes.Strings.settings_category_interface,
            settings = listOf(
                addThemeMode(),
                addFavoriteSwipe(),
            )
        ),
        SettingsCategory(
            id = "sync",
            title = SharedRes.Strings.settings_category_data,
            settings = listOf(
                addSync(),

            )
        ),

        SettingsCategory(
            id = "info",
            title = null,
            settings = listOf(
                addAppInfo(),
                addDeviceInfo(),
            )
        )
    )

    private fun addThemeMode(): Setting {
        val themeModeName = ThemeMode.fromId(localSettings.themeModeId).name

        return Setting.Options(
            key = "theme_mode",
            title = SharedRes.Strings.settings_theme_title,
            formattedSummary = StringRefWithArgs(SharedRes.Strings.settings_summary_current),
            options = listOf(
                SettingOption(ThemeMode.LIGHT.name, SharedRes.Strings.settings_theme_option_light),
                SettingOption(ThemeMode.DARK.name, SharedRes.Strings.settings_theme_option_dark),
                SettingOption(ThemeMode.SYSTEM.name, SharedRes.Strings.settings_theme_option_system),
            ),
            dialogTitle = SharedRes.Strings.settings_theme_dialog_title,
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

    private fun addAppInfo() : Setting {
         val info = repository.platformInfo.getAppInfo()
        return Setting.Info(
            key = "app_info",
            title = SharedRes.Strings.settings_appversion_title,
            summary = SharedRes.Strings.settings_appversion_summary,
            info = info.appVersion
        )
    }

    private fun addDeviceInfo() : Setting {
        val info = repository.platformInfo.getAppInfo()
        return Setting.Info(
            key = "device_info",
            title = SharedRes.Strings.settings_deviceinfo_title,
            summary = SharedRes.Strings.settings_deviceinfo_summary,
            info = "${info.deviceModel} (${info.platformName} ${info.osVersion})"
        )
    }
}


data class SettingsCategory(
    val id: String,
    val title: StringRef? = null,
    val settings: List<Setting>
)




