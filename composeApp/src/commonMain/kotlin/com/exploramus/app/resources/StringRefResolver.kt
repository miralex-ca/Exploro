package com.exploramus.app.resources

import androidx.compose.runtime.Composable
import com.exploramus.shared.resources.SharedRes
import com.exploramus.shared.resources.StringRef
import com.exploramus.shared.resources.StringRefWithArgs

object StringRefResolver {
    @Composable
    fun resolve(ref: StringRef): String = when (ref) {
        SharedRes.Strings.settings_theme_title -> stringRes("settings_theme_title")
        SharedRes.Strings.settings_theme_option_system -> stringRes("settings_theme_option_system")
        SharedRes.Strings.settings_theme_option_dark -> stringRes("settings_theme_option_dark")
        SharedRes.Strings.settings_theme_option_light -> stringRes("settings_theme_option_light")
        SharedRes.Strings.settings_theme_dialog_title -> stringRes("settings_theme_dialog_title")
        SharedRes.Strings.settings_favorite_swipe_title -> stringRes("settings_favorite_swipe_title")
        SharedRes.Strings.settings_favorite_swipe_summaryOn -> stringRes("settings_favorite_swipe_summaryOn")
        SharedRes.Strings.settings_favorite_swipe_summaryOff -> stringRes("settings_favorite_swipe_summaryOff")
        SharedRes.Strings.settings_sync_title -> stringRes("settings_sync_title")
        SharedRes.Strings.settings_sync_summary -> stringRes("settings_sync_summary")
        SharedRes.Strings.settings_sync_dialog_title -> stringRes("settings_sync_dialog_title")
        SharedRes.Strings.settings_sync_dialog_message -> stringRes("settings_sync_dialog_message")
        SharedRes.Strings.settings_category_interface -> stringRes("settings_category_interface")
        SharedRes.Strings.settings_category_data -> stringRes("settings_category_data")
        SharedRes.Strings.settings_appversion_title -> stringRes("settings_appversion_title")
        SharedRes.Strings.settings_appversion_summary -> stringRes("settings_appversion_summary")
        SharedRes.Strings.settings_deviceinfo_title -> stringRes("settings_deviceinfo_title")
        SharedRes.Strings.settings_deviceinfo_summary -> stringRes("settings_deviceinfo_summary")
        SharedRes.Strings.settings_sync_in_progress -> stringRes("settings_sync_in_progress")
        else -> ref.simpleName()
    }

    @Composable
    fun resolve(ref: StringRefWithArgs, vararg args: String): String = when (ref.ref) {
        SharedRes.Strings.settings_summary_current_fmt ->
            stringRes("settings_summary_current", *args)
        else -> ref.ref.simpleName()
    }

    @Composable
    fun resolve(ref: StringRefWithArgs, arg: String): String = when (ref.ref) {
        SharedRes.Strings.settings_sync_last_fmt ->
            stringRes("settings_sync_last", arg)

        SharedRes.Strings.settings_sync_success_fmt ->
            stringRes("settings_sync_success", arg)

        SharedRes.Strings.settings_sync_failed_fmt ->
            stringRes("settings_sync_failed", arg)

        else -> ref.ref.simpleName()
    }
}
