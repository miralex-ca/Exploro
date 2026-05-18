package com.muralex.myapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.muralex.myapp.R

object Strings {
    val homeTitle @Composable get() = stringRes(R.string.screen_home_title)
    val favoritesTitle @Composable get() = stringRes(R.string.screen_favorites_title)
    val searchTitle @Composable get() = stringRes(R.string.screen_search_title)
    val settingsTitle @Composable get() = stringRes(R.string.screen_settings_title)

    val themeModeLight @Composable get() = "Light mode"
    val themeModeDark @Composable get() = "Dark mode"
    val themeModeSystem @Composable get() = "System mode"
}

@Composable
fun stringRes(
    resId: Int,
    vararg formatArgs: Any
): String {
    return stringResource(resId, *formatArgs)
}