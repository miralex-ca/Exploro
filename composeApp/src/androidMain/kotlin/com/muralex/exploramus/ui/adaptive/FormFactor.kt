package com.muralex.exploramus.ui.adaptive

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.window.core.layout.WindowSizeClass

val LocalFormFactor = compositionLocalOf { FormFactor.PHONE_PORTRAIT }

enum class FormFactor {
    PHONE_PORTRAIT,
    PHONE_LANDSCAPE,
    TABLET_PORTRAIT,
    TABLET_LANDSCAPE
}

val FormFactor.isPhone get() = this == FormFactor.PHONE_PORTRAIT || this == FormFactor.PHONE_LANDSCAPE
val FormFactor.isTablet get() = this == FormFactor.TABLET_PORTRAIT || this == FormFactor.TABLET_LANDSCAPE
val FormFactor.isLandscape get() = this == FormFactor.PHONE_LANDSCAPE || this == FormFactor.TABLET_LANDSCAPE
val FormFactor.useBottomBar get() = this == FormFactor.PHONE_PORTRAIT
val FormFactor.useNavRail get() = this == FormFactor.PHONE_LANDSCAPE || this == FormFactor.TABLET_PORTRAIT
val FormFactor.useDrawer get() = this == FormFactor.TABLET_LANDSCAPE

@Composable
fun rememberFormFactor(): FormFactor {
    val adaptiveInfo = currentWindowAdaptiveInfo()
    return remember(adaptiveInfo) {
        val isCompactWidth = !adaptiveInfo.windowSizeClass
            .isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)
        val isCompactHeight = !adaptiveInfo.windowSizeClass
            .isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND)
        val isExpandedWidth = adaptiveInfo.windowSizeClass
            .isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)

        when {
            isExpandedWidth && !isCompactHeight -> FormFactor.TABLET_LANDSCAPE
            isCompactHeight -> FormFactor.PHONE_LANDSCAPE
            !isCompactWidth -> FormFactor.TABLET_PORTRAIT
            else -> FormFactor.PHONE_PORTRAIT
        }
    }
}