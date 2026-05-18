package com.muralex.myapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muralex.myapp.navigation.Router
import com.muralex.myapp.ui.theme.AppTheme
import com.muralex.myapp.viewmodel.Navigation
import com.muralex.myapp.viewmodel.appenvironment.AppEnvironment


@Composable
fun MainComposable(
    navigation: Navigation
) {
    val appEnvironment by navigation.stateProvider
        .getAppEnvironmentFlow()
        .collectAsStateWithLifecycle()

    CompositionLocalProvider(
        LocalAppEnvironment provides appEnvironment
    ) {
        AppTheme(themeMode = appEnvironment.themeMode) {
            navigation.Router()
        }
    }
}

val LocalAppEnvironment = staticCompositionLocalOf<AppEnvironment> {
    error("AppConfig not provided")
}