package com.muralex.myapp.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.muralex.myapp.R

@Composable
fun stringRes(
    resId: Int,
    vararg formatArgs: Any
): String {
    return stringResource(resId, *formatArgs)
}

object Strings {
    val homeTitle @Composable get() = stringRes(R.string.screen_home_title)
    val favoritesTitle @Composable get() = stringRes(R.string.screen_favorites_title)
    val searchTitle @Composable get() = stringRes(R.string.screen_search_title)
    val settingsTitle @Composable get() = stringRes(R.string.screen_settings_title)

    val navBrowse @Composable get() = stringRes(R.string.nav_browse)
    val navFavorites @Composable get() = stringRes(R.string.nav_favorites)

    val appStartupErrorTitle @Composable get() = stringRes(R.string.app_startup_error_title)
    val appStartupErrorSyncDesc @Composable get() = stringRes(R.string.app_startup_error_sync_desc)
    val appStartupErrorDesc @Composable get() = stringRes(R.string.app_startup_error_desc)
    val appErrorTryAgain @Composable get() = stringRes(R.string.app_error_try_again)

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







