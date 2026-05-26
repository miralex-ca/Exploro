package com.muralex.myapp.resources

import androidx.compose.runtime.Composable
import com.muralex.myapp.R
import com.muralex.myapp.viewmodel.resources.SharedRes
import com.muralex.myapp.viewmodel.resources.StringRef
import com.muralex.myapp.viewmodel.resources.StringRefWithArgs

object StringRefResolver {
    @Composable
    fun resolve(ref: StringRef): String = when (ref) {
        SharedRes.Strings.settings_theme_title -> stringRes(R.string.settings_theme_title)
        SharedRes.Strings.settings_theme_option_system -> stringRes(R.string.settings_theme_option_system)
        SharedRes.Strings.settings_theme_option_dark -> stringRes(R.string.settings_theme_option_dark)
        SharedRes.Strings.settings_theme_option_light -> stringRes(R.string.settings_theme_option_light)
        SharedRes.Strings.settings_theme_dialog_title -> stringRes(R.string.settings_theme_dialog_title)
        SharedRes.Strings.settings_favorite_swipe_title -> stringRes(R.string.settings_favorite_swipe_title)
        SharedRes.Strings.settings_favorite_swipe_summaryOn -> stringRes(R.string.settings_favorite_swipe_summaryOn)
        SharedRes.Strings.settings_favorite_swipe_summaryOff -> stringRes(R.string.settings_favorite_swipe_summaryOff)
        SharedRes.Strings.settings_sync_title -> stringRes(R.string.settings_sync_title)
        SharedRes.Strings.settings_sync_summary -> stringRes(R.string.settings_sync_summary)
        SharedRes.Strings.settings_sync_dialog_title -> stringRes(R.string.settings_sync_dialog_title)
        SharedRes.Strings.settings_sync_dialog_message -> stringRes(R.string.settings_sync_dialog_message)
        SharedRes.Strings.settings_category_interface -> stringRes(R.string.settings_category_interface)
        SharedRes.Strings.settings_category_data -> stringRes(R.string.settings_category_data)
        SharedRes.Strings.settings_appversion_title -> stringRes(R.string.settings_appversion_title)
        SharedRes.Strings.settings_appversion_summary -> stringRes(R.string.settings_appversion_summary)
        SharedRes.Strings.settings_deviceinfo_title -> stringRes(R.string.settings_deviceinfo_title)
        SharedRes.Strings.settings_deviceinfo_summary -> stringRes(R.string.settings_deviceinfo_summary)
        SharedRes.Strings.settings_sync_in_progress -> stringRes(R.string.settings_sync_in_progress)
        else -> ref.simpleName()
    }

    @Composable
    fun resolve(ref: StringRefWithArgs, arg: String): String = when (ref.ref) {
        SharedRes.Strings.settings_sync_last_formatted ->
            stringRes(R.string.settings_sync_last, arg)

        SharedRes.Strings.settings_sync_success_formatted ->
            stringRes(R.string.settings_sync_success, arg)

        SharedRes.Strings.settings_sync_failed_formatted ->
            stringRes(R.string.settings_sync_failed, arg)

        else -> ref.ref.simpleName()
    }

    @Composable
    fun resolve(ref: StringRefWithArgs, vararg args: String): String = when (ref.ref) {
        SharedRes.Strings.settings_summary_current_formatted ->
            stringRes(R.string.settings_summary_current, *args)

        else -> ref.ref.simpleName()
    }
}