package com.muralex.myapp.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun AppTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    content: @Composable () -> Unit
) {

    val darkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    val colorScheme = //LightColorScheme
        if (darkTheme)  DarkColorScheme
        else LightColorScheme

    val appColors =
        if (darkTheme)  DarkAppColors
        else LightAppColors

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

enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM
}