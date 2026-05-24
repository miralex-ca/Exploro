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

    val themeModeLight @Composable get() = "Light mode"
    val themeModeDark @Composable get() = "Dark mode"
    val themeModeSystem @Composable get() = "System mode"

    val appStartupErrorTitle @Composable get() = "Something went wrong"
    val appStartupErrorSyncDes @Composable get() = "We couldn't load your data. Please check your connection and try again."
    val appStartupErrorDes @Composable get() = "We couldn't load your data. Please try again later."
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
        SharedRes.Strings.settings_theme_title -> Strings.settingsTitle
        SharedRes.Strings.settings_theme_option_light -> Strings.themeModeLight
        SharedRes.Strings.settings_theme_option_dark -> Strings.themeModeDark
        SharedRes.Strings.settings_theme_option_system -> Strings.themeModeSystem
        else -> ref.simpleName()
    }
}


