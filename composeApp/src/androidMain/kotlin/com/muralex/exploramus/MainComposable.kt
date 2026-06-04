package com.muralex.exploramus

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muralex.exploramus.navigation.Router
import com.muralex.exploramus.ui.adaptive.AppLayout
import com.muralex.exploramus.ui.adaptive.LocalAppLayout
import com.muralex.exploramus.ui.adaptive.LocalFormFactor
import com.muralex.exploramus.ui.adaptive.rememberFormFactor
import com.muralex.exploramus.ui.theme.AppTheme
import com.muralex.exploramus.viewmodel.appstate.AppEnvironment
import com.muralex.exploramus.viewmodel.core.Navigation

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