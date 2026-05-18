package com.muralex.myapp.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun AppTheme(
    themesMode: ThemesMode = ThemesMode.SYSTEM,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themesMode) {
        ThemesMode.LIGHT -> false
        ThemesMode.DARK -> true
        ThemesMode.SYSTEM -> isSystemInDarkTheme()
    }

    val colorScheme =
        if (darkTheme) DarkColorScheme
        else LightColorScheme

    val appColors =
        if (darkTheme) DarkAppColors
        else LightAppColors

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
    ) {
        CompositionLocalProvider(
            LocalAppColors provides appColors
        ) {
            content()
        }
    }
}

val MaterialTheme.appColors: AppColors
    @Composable
    @ReadOnlyComposable
    get() = LocalAppColors.current

enum class ThemesMode {
    LIGHT,
    DARK,
    SYSTEM
}