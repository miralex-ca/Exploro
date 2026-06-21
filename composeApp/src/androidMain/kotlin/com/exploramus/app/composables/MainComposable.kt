package com.exploramus.app.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exploramus.app.composables.navigation.Router
import com.exploramus.app.design.adaptive.AppLayout
import com.exploramus.app.design.adaptive.LocalAppLayout
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.adaptive.rememberFormFactor
import com.exploramus.app.design.theme.AppTheme
import com.exploramus.shared.viewmodel.appstate.AppEnvironment
import com.exploramus.shared.viewmodel.core.Navigation

@Composable
fun MainComposable(
    navigation: Navigation
) {
    val appEnvironment by navigation.stateProvider
        .getAppEnvironmentFlow()
        .collectAsStateWithLifecycle()

    val formFactor = rememberFormFactor()

    CompositionLocalProvider(
        LocalAppEnvironment provides appEnvironment,
        LocalFormFactor provides formFactor,
        LocalAppLayout provides AppLayout.build(formFactor),
    ) {
        AppTheme(themeMode = appEnvironment.themeMode) {
            navigation.Router()
        }
    }
}

val LocalAppEnvironment = staticCompositionLocalOf<AppEnvironment> {
    error("AppConfig not provided")
}