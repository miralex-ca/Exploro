package com.exploramus.app.resources

import androidx.compose.runtime.Composable
import com.exploramus.shared.resources.SharedRes
import com.exploramus.shared.resources.StringRef
import com.exploramus.shared.resources.StringRefWithArgs
import exploramus.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

object StringRefResolver {
    @Composable
    fun resolve(ref: StringRef): String = when (ref) {
        SharedRes.Strings.settings_theme_title -> stringResource(Res.string.settings_theme_title)
        SharedRes.Strings.settings_theme_option_system -> stringResource(Res.string.settings_theme_option_system)
        SharedRes.Strings.settings_theme_option_dark -> stringResource(Res.string.settings_theme_option_dark)
        SharedRes.Strings.settings_theme_option_light -> stringResource(Res.string.settings_theme_option_light)
        SharedRes.Strings.settings_theme_dialog_title -> stringResource(Res.string.settings_theme_dialog_title)
        SharedRes.Strings.settings_favorite_swipe_title -> stringResource(Res.string.settings_favorite_swipe_title)
        SharedRes.Strings.settings_favorite_swipe_summaryOn -> stringResource(Res.string.settings_favorite_swipe_summaryOn)
        SharedRes.Strings.settings_favorite_swipe_summaryOff -> stringResource(Res.string.settings_favorite_swipe_summaryOff)
        SharedRes.Strings.settings_sync_title -> stringResource(Res.string.settings_sync_title)
        SharedRes.Strings.settings_sync_summary -> stringResource(Res.string.settings_sync_summary)
        SharedRes.Strings.settings_sync_dialog_title -> stringResource(Res.string.settings_sync_dialog_title)
        SharedRes.Strings.settings_sync_dialog_message -> stringResource(Res.string.settings_sync_dialog_message)
        SharedRes.Strings.settings_category_interface -> stringResource(Res.string.settings_category_interface)
        SharedRes.Strings.settings_category_data -> stringResource(Res.string.settings_category_data)
        SharedRes.Strings.settings_appversion_title -> stringResource(Res.string.settings_appversion_title)
        SharedRes.Strings.settings_appversion_summary -> stringResource(Res.string.settings_appversion_summary)
        SharedRes.Strings.settings_deviceinfo_title -> stringResource(Res.string.settings_deviceinfo_title)
        SharedRes.Strings.settings_deviceinfo_summary -> stringResource(Res.string.settings_deviceinfo_summary)
        SharedRes.Strings.settings_sync_in_progress -> stringResource(Res.string.settings_sync_in_progress)
        else -> ref.simpleName()
    }

    @Composable
    fun resolve(ref: StringRefWithArgs, vararg args: String): String = when (ref.ref) {
        SharedRes.Strings.settings_summary_current_fmt ->
            stringResource(Res.string.settings_summary_current, *args)
        else -> ref.ref.simpleName()
    }

    @Composable
    fun resolve(ref: StringRefWithArgs, arg: String): String = when (ref.ref) {
        SharedRes.Strings.settings_sync_last_fmt ->
            stringResource(Res.string.settings_sync_last, arg)

        SharedRes.Strings.settings_sync_success_fmt ->
            stringResource(Res.string.settings_sync_success, arg)

        SharedRes.Strings.settings_sync_failed_fmt ->
            stringResource(Res.string.settings_sync_failed, arg)

        else -> ref.ref.simpleName()
    }
}
