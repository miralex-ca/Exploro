package com.exploramus.app.composables.navigation.ui.transitions

import androidx.compose.runtime.Composable
import com.exploramus.app.design.adaptive.rememberFormFactor
import com.exploramus.app.design.adaptive.useBottomBar

@Composable
actual fun currentScreenTransitionStyle(): ScreenTransitionStyle {
    val formFactor = rememberFormFactor()
    return if (formFactor.useBottomBar) {
        ScreenTransitionStyle.IOS
    } else {
        ScreenTransitionStyle.IOS_TABLET
    }
}
