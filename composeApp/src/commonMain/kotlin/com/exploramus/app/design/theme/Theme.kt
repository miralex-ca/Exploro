package com.exploramus.app.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import com.exploramus.core.models.ThemeMode

@Composable
fun AppTheme(
    themeMode: ThemeMode = ThemeMode.LIGHT,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    val colorScheme =
        if (darkTheme) DarkColorScheme
        else LightColorScheme

    val appColors =
        if (darkTheme) DarkAppColors
        else LightAppColors

    SystemAppearance(isDark = darkTheme)

    val rippleConfiguration = RippleConfiguration(
        color = if (darkTheme) darkThemeRippleColor else lightThemeRippleColor,
        rippleAlpha = if (darkTheme) {
            RippleAlpha(
                draggedAlpha = 0.16f,
                focusedAlpha = 0.12f,
                hoveredAlpha = 0.08f,
                pressedAlpha = 0.16f
            )
        } else {
            RippleAlpha(
                draggedAlpha = 0.14f,
                focusedAlpha = 0.10f,
                hoveredAlpha = 0.07f,
                pressedAlpha = 0.12f
            )
        }
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
    ) {
        CompositionLocalProvider(
            LocalAppColors provides appColors,
            LocalRippleConfiguration provides rippleConfiguration
        ) {
            content()
        }
    }
}
