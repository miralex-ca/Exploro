package com.muralex.myapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.muralex.myapp.R
import com.muralex.myapp.viewmodel.resources.SharedRes
import com.muralex.myapp.viewmodel.resources.StringRef

object Strings {
    val homeTitle @Composable get() = stringRes(R.string.screen_home_title)
    val favoritesTitle @Composable get() = stringRes(R.string.screen_favorites_title)
    val searchTitle @Composable get() = stringRes(R.string.screen_search_title)
    val settingsTitle @Composable get() = stringRes(R.string.screen_settings_title)

    val navBrowse @Composable get() = stringRes(R.string.nav_browse)
    val navFavorites @Composable get() = stringRes(R.string.nav_favorites)

    val settingsThemeTitle @Composable get() = stringRes(R.string.settings_theme_title)
    val settingsThemeSummary @Composable get() = stringRes(R.string.settings_theme_summary)
    val settingsThemeOptionSystem @Composable get() = stringRes(R.string.settings_theme_option_system)
    val settingsThemeOptionDark @Composable get() = stringRes(R.string.settings_theme_option_dark)
    val settingsThemeOptionLight @Composable get() = stringRes(R.string.settings_theme_option_light)
    val settingsThemeDialogTitle @Composable get() = stringRes(R.string.settings_theme_dialog_title)

    val settingsFavoriteSwipeTitle @Composable get() = stringRes(R.string.settings_favorite_swipe_title)
    val settingsFavoriteSwipeSummary @Composable get() = stringRes(R.string.settings_favorite_swipe_summary)
    val settingsFavoriteSwipeSummaryOn @Composable get() = stringRes(R.string.settings_favorite_swipe_summaryOn)
    val settingsFavoriteSwipeSummaryOff @Composable get() = stringRes(R.string.settings_favorite_swipe_summaryOff)

    val settingsSyncTitle @Composable get() = stringRes(R.string.settings_sync_title)
    val settingsSyncSummary @Composable get() = stringRes(R.string.settings_sync_summary)
    val settingsSyncDialogTitle @Composable get() = stringRes(R.string.settings_sync_dialog_title)
    val settingsSyncDialogMessage @Composable get() = stringRes(R.string.settings_sync_dialog_message)

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
fun StringRef.asStringWithArgs(vararg args: Any): String = StringRefResolver.resolve(this).format(*args)

object StringRefResolver {
    @Composable
    fun resolve(ref: StringRef): String = when (ref) {
        SharedRes.Strings.settings_theme_title -> Strings.settingsThemeTitle
        SharedRes.Strings.settings_theme_summary -> Strings.settingsThemeSummary
        SharedRes.Strings.settings_theme_option_system -> Strings.settingsThemeOptionSystem
        SharedRes.Strings.settings_theme_option_dark -> Strings.settingsThemeOptionDark
        SharedRes.Strings.settings_theme_option_light -> Strings.settingsThemeOptionLight
        SharedRes.Strings.settings_theme_dialog_title -> Strings.settingsThemeDialogTitle
        SharedRes.Strings.settings_favorite_swipe_title -> Strings.settingsFavoriteSwipeTitle
        SharedRes.Strings.settings_favorite_swipe_summary -> Strings.settingsFavoriteSwipeSummary
        SharedRes.Strings.settings_favorite_swipe_summaryOn -> Strings.settingsFavoriteSwipeSummaryOn
        SharedRes.Strings.settings_favorite_swipe_summaryOff -> Strings.settingsFavoriteSwipeSummaryOff
        SharedRes.Strings.settings_sync_title -> Strings.settingsSyncTitle
        SharedRes.Strings.settings_sync_summary -> Strings.settingsSyncSummary
        SharedRes.Strings.settings_sync_dialog_title -> Strings.settingsSyncDialogTitle
        SharedRes.Strings.settings_sync_dialog_message -> Strings.settingsSyncDialogMessage
        else -> ref.simpleName()
    }
}
