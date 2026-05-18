package com.muralex.myapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muralex.myapp.navigation.Router
import com.muralex.myapp.ui.theme.AppTheme
import com.muralex.myapp.ui.theme.ThemesMode
import com.muralex.myapp.viewmodel.DKMPViewModel
import com.muralex.myapp.viewmodel.appconfig.AppConfig
import com.muralex.myapp.viewmodel.appconfig.ThemeStatus


@Composable
fun MainComposable(
    model: DKMPViewModel
) {
    val dkmpNav = model.navigation

    val appConfigFlow = model.navigation.stateProvider.getAppConfigStateFlow()
    val appConfig by appConfigFlow.collectAsStateWithLifecycle()

    CompositionLocalProvider(
        LocalAppConfig provides appConfig
    ) {
        AppTheme(
            themesMode = themeFromStatus(appConfig.themeMode)
        ) {
            dkmpNav.Router()
        }
    }

}

val LocalAppConfig = staticCompositionLocalOf<AppConfig> {
    error("AppConfig not provided")
}

fun themeFromStatus(status: ThemeStatus): ThemesMode {
    return when (status) {
        ThemeStatus.SYSTEM -> ThemesMode.SYSTEM
        ThemeStatus.LIGHT -> ThemesMode.LIGHT
        ThemeStatus.DARK -> ThemesMode.DARK
    }
}