package com.muralex.exploramus.viewmodel.screens.settings.builder

import com.muralex.core.common.result.DataResult
import com.muralex.data.common.PlatformInfoProvider
import com.muralex.data.repository.sources.localsettings.MySettings
import com.muralex.models.ThemeMode
import com.muralex.exploramus.viewmodel.resources.FormattedText
import com.muralex.exploramus.viewmodel.resources.SharedRes
import com.muralex.exploramus.viewmodel.utils.toFormattedDate


class InterfaceSettingsCategory(private val localSettings: MySettings) {
    fun build() = SettingsCategory(
        id = CATEGORY_ID,
        title = SharedRes.Strings.settings_category_interface,
        settings = listOf(
            addThemeMode(),
            addFavoriteSwipe(),
        )
    )

    private fun addThemeMode(): Setting {
        val themeModeName = ThemeMode.Companion.fromId(localSettings.themeModeId).name

        return Setting.Options(
            key = THEME_SETTING_ID,
            title = SharedRes.Strings.settings_theme_title,
            formattedSummary = FormattedText.Ref.of(SharedRes.Strings.settings_summary_current_formatted),
            options = listOf(
                SettingOption(ThemeMode.LIGHT.name, SharedRes.Strings.settings_theme_option_light),
                SettingOption(ThemeMode.DARK.name, SharedRes.Strings.settings_theme_option_dark),
                SettingOption(
                    ThemeMode.SYSTEM.name,
                    SharedRes.Strings.settings_theme_option_system
                ),
            ),
            dialogTitle = SharedRes.Strings.settings_theme_dialog_title,
            selectedValue = themeModeName,
            onSelect = { SettingAction.SetThemeMode(it) }
        )
    }

    private fun addFavoriteSwipe(): Setting {
        val isEnabled = localSettings.favoriteSwipeEnabled
        return Setting.Switch(
            key = FAVORITE_SWIPE_SETTING_ID,
            title = SharedRes.Strings.settings_favorite_swipe_title,
            summaryOn = SharedRes.Strings.settings_favorite_swipe_summaryOn,
            summaryOff = SharedRes.Strings.settings_favorite_swipe_summaryOff,
            defaultValue = false,
            value = isEnabled,
            onToggle = { SettingAction.SetFavoriteSwipe(!isEnabled) }
        )
    }

    companion object {
        const val CATEGORY_ID = "interface"
        const val THEME_SETTING_ID = "theme_mode"
        const val FAVORITE_SWIPE_SETTING_ID = "favorite_swipe"
    }
}

class DataSettingsCategory(private val localSettings: MySettings) {
    fun build() = SettingsCategory(
        id = CATEGORY_ID,
        title = SharedRes.Strings.settings_category_data,
        settings = listOf(
            addSync(),
        )
    )

    private fun addSync(): Setting {
        val lastUpdate = localSettings.listCacheTimestamp
        val timeLabel = lastUpdate.toFormattedDate()
        val formattedSummary = buildSyncSummary(timeLabel)

        return Setting.Action(
            key = SYNC_SETTING_ID,
            title = SharedRes.Strings.settings_sync_title,
            summary = SharedRes.Strings.settings_sync_summary,
            dialogTitle = SharedRes.Strings.settings_sync_dialog_title,
            dialogMessage = SharedRes.Strings.settings_sync_dialog_message,
            formattedSummary = formattedSummary,
            onClick = { SettingAction.SyncData }
        )
    }

    companion object {
        const val CATEGORY_ID = "app_data"
        const val SYNC_SETTING_ID = "sync_data"

        fun buildSyncResultSummary(
            result: DataResult<Unit>,
            timeLabel: String?
        ): FormattedText.WithString? {
            val ref = when (result) {
                is DataResult.Success -> SharedRes.Strings.settings_sync_success_formatted
                is DataResult.Error -> SharedRes.Strings.settings_sync_failed_formatted
            }
            return timeLabel?.let { FormattedText.WithString.of(ref, it) }
        }

        private fun buildSyncSummary(timeLabel: String?) = timeLabel?.let {
            FormattedText.WithString.of(SharedRes.Strings.settings_sync_last_formatted, it)
        }
    }
}

class InfoSettingsCategory(private val platformInfo: PlatformInfoProvider) {
    fun build() = SettingsCategory(
        id = CATEGORY_ID,
        title = null,
        settings = listOf(
            addAppInfo(),
            addDeviceInfo(),
        )
    )

    private fun addAppInfo(): Setting {
        val info = platformInfo.getAppInfo()
        return Setting.Info(
            key = APP_INFO_ID,
            title = SharedRes.Strings.settings_appversion_title,
            summary = SharedRes.Strings.settings_appversion_summary,
            info = info.appVersion
        )
    }

    private fun addDeviceInfo(): Setting {
        val info = platformInfo.getAppInfo()
        return Setting.Info(
            key = DEVICE_INFO_ID,
            title = SharedRes.Strings.settings_deviceinfo_title,
            summary = SharedRes.Strings.settings_deviceinfo_summary,
            info = "${info.deviceModel} (${info.platformName} ${info.osVersion})"
        )
    }

    companion object {
        const val CATEGORY_ID = "info"
        const val APP_INFO_ID = "app_info"
        const val DEVICE_INFO_ID = "device_info"
    }
}

