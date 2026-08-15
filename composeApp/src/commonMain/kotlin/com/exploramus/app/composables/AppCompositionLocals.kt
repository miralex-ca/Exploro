package com.exploramus.app.composables

import androidx.compose.runtime.staticCompositionLocalOf
import com.exploramus.shared.viewmodel.appstate.AppEnvironment

val LocalAppEnvironment = staticCompositionLocalOf<AppEnvironment> {
    error("AppConfig not provided")
}
