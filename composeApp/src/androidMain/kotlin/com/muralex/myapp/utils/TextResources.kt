package com.muralex.myapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.muralex.myapp.R
import com.muralex.myapp.viewmodel.resources.SharedRes
import com.muralex.myapp.viewmodel.resources.StringRef
import com.muralex.myapp.viewmodel.resources.StringRefWithArgs


object Strings {
    val homeTitle @Composable get() = stringRes(R.string.screen_home_title)
    val favoritesTitle @Composable get() = stringRes(R.string.screen_favorites_title)
    val searchTitle @Composable get() = stringRes(R.string.screen_search_title)
    val settingsTitle @Composable get() = stringRes(R.string.screen_settings_title)

    val navBrowse @Composable get() = stringRes(R.string.nav_browse)
    val navFavorites @Composable get() = stringRes(R.string.nav_favorites)

    val settingsThemeTitle @Composable get() = stringRes(R.string.settings_theme_title)
    val settingsThemeOptionSystem @Composable get() = stringRes(R.string.settings_theme_option_system)
    val settingsThemeOptionDark @Composable get() = stringRes(R.string.settings_theme_option_dark)
    val settingsThemeOptionLight @Composable get() = stringRes(R.string.settings_theme_option_light)
    val settingsThemeDialogTitle @Composable get() = stringRes(R.string.settings_theme_dialog_title)

    val settingsFavoriteSwipeTitle @Composable get() = stringRes(R.string.settings_favorite_swipe_title)
    val settingsFavoriteSwipeSummaryOn @Composable get() = stringRes(R.string.settings_favorite_swipe_summaryOn)
    val settingsFavoriteSwipeSummaryOff @Composable get() = stringRes(R.string.settings_favorite_swipe_summaryOff)

    val settingsSyncTitle @Composable get() = stringRes(R.string.settings_sync_title)
    val settingsSyncSummary @Composable get() = stringRes(R.string.settings_sync_summary)
    val settingsSyncDialogTitle @Composable get() = stringRes(R.string.settings_sync_dialog_title)
    val settingsSyncDialogMessage @Composable get() = stringRes(R.string.settings_sync_dialog_message)

    val settingsInterface @Composable get() = stringRes(R.string.settings_category_interface)
    val settingsData @Composable get() = stringRes(R.string.settings_category_data)

    val settingsAppversionTitle @Composable get() = stringRes(R.string.settings_appversion_title)
    val settingsAppversionSummary @Composable get() = stringRes(R.string.settings_appversion_summary)
    val settingsDeviceInfoTitle @Composable get() = stringRes(R.string.settings_deviceinfo_title)
    val settingsDeviceInfoSummary @Composable get() = stringRes(R.string.settings_deviceinfo_summary)

    val appStartupErrorTitle @Composable get() = stringRes(R.string.app_startup_error_title)
    val appStartupErrorSyncDesc @Composable get() = stringRes(R.string.app_startup_error_sync_desc)
    val appStartupErrorDesc @Composable get() = stringRes(R.string.app_startup_error_desc)
    val appErrorTryAgain @Composable get() = stringRes(R.string.app_error_try_again)

    val themeModeLight @Composable get() = stringRes(R.string.theme_mode_light)
    val themeModeDark @Composable get() = stringRes(R.string.theme_mode_dark)
    val themeModeSystem @Composable get() = stringRes(R.string.theme_mode_system)

    val noCountriesFound @Composable get() = stringRes(R.string.no_countries_found)
    val detailsCoatOfArms @Composable get() = stringRes(R.string.details_coat_of_arms)
    val commonClose @Composable get() = stringRes(R.string.common_close)
    val commonConfirm @Composable get() = stringRes(R.string.common_confirm)
    val commonCancel @Composable get() = stringRes(R.string.common_cancel)

    val detailLabelLocation @Composable get() = stringRes(R.string.detail_label_location)
    val detailLabelArea @Composable get() = stringRes(R.string.detail_label_area)
    val detailLabelPopulation @Composable get() = stringRes(R.string.detail_label_population)
    val detailLabelLanguages @Composable get() = stringRes(R.string.detail_label_languages)
    val detailLabelCurrency @Composable get() = stringRes(R.string.detail_label_currency)
    val detailLabelTimezones @Composable get() = stringRes(R.string.detail_label_timezones)
    val detailLabelCapital @Composable get() = stringRes(R.string.detail_label_capital)
    val detailLabelRegion @Composable get() = stringRes(R.string.detail_label_region)


    object Formatted {
        @Composable
        fun settingsSummaryCurrent(vararg args: String) =
            stringRes(R.string.settings_summary_current, *args)
    }
}

@Composable
fun stringRes(
    resId: Int,
    vararg formatArgs: Any
): String {
    return stringResource(resId, *formatArgs)
}

@Composable
fun StringRef.asString(): String = StringRefResolver.resolve(this)

@Composable
fun StringRefWithArgs.asStringWithArgs(firstArg: String, vararg otherArgs: String): String =
    StringRefResolver.resolve(this, firstArg, *otherArgs)

object StringRefResolver {
    @Composable
    fun resolve(ref: StringRef): String = when (ref) {
        SharedRes.Strings.settings_theme_title -> Strings.settingsThemeTitle
        SharedRes.Strings.settings_theme_option_system -> Strings.settingsThemeOptionSystem
        SharedRes.Strings.settings_theme_option_dark -> Strings.settingsThemeOptionDark
        SharedRes.Strings.settings_theme_option_light -> Strings.settingsThemeOptionLight
        SharedRes.Strings.settings_theme_dialog_title -> Strings.settingsThemeDialogTitle
        SharedRes.Strings.settings_favorite_swipe_title -> Strings.settingsFavoriteSwipeTitle
        SharedRes.Strings.settings_favorite_swipe_summaryOn -> Strings.settingsFavoriteSwipeSummaryOn
        SharedRes.Strings.settings_favorite_swipe_summaryOff -> Strings.settingsFavoriteSwipeSummaryOff
        SharedRes.Strings.settings_sync_title -> Strings.settingsSyncTitle
        SharedRes.Strings.settings_sync_summary -> Strings.settingsSyncSummary
        SharedRes.Strings.settings_sync_dialog_title -> Strings.settingsSyncDialogTitle
        SharedRes.Strings.settings_sync_dialog_message -> Strings.settingsSyncDialogMessage
        SharedRes.Strings.settings_category_interface -> Strings.settingsInterface
        SharedRes.Strings.settings_category_data -> Strings.settingsData
        SharedRes.Strings.settings_appversion_title -> Strings.settingsAppversionTitle
        SharedRes.Strings.settings_appversion_summary -> Strings.settingsAppversionSummary
        SharedRes.Strings.settings_deviceinfo_title -> Strings.settingsDeviceInfoTitle
        SharedRes.Strings.settings_deviceinfo_summary -> Strings.settingsDeviceInfoSummary
        else -> ref.simpleName()
    }

    @Composable
    fun resolve(ref: StringRefWithArgs, vararg args: String): String = when (ref.ref) {
        SharedRes.Strings.settings_summary_current -> Strings.Formatted.settingsSummaryCurrent(*args)
        else -> ref.ref.simpleName()
    }
}
