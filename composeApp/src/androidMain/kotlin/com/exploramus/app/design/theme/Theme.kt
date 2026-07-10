package com.exploramus.app.design.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
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

val MaterialTheme.appColors: AppColors
    @Composable
    @ReadOnlyComposable
    get() = LocalAppColors.current
