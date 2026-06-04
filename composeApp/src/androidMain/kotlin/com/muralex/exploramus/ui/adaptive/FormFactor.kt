package com.muralex.exploramus.ui.adaptive

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.window.core.layout.WindowSizeClass

val LocalFormFactor = staticCompositionLocalOf<FormFactor> {
    error("LocalFormFactor not provided — wrap with CompositionLocalProvider at root")
}


data class FormFactor(
    val widthType: WidthType,
    val heightType: HeightType,
    val orientation: ScreenOrientation
)

enum class WidthType {
    COMPACT,
    MEDIUM,
    EXPANDED
}

enum class HeightType {
    COMPACT,
    MEDIUM,
    EXPANDED
}

enum class ScreenOrientation {
    PORTRAIT,
    LANDSCAPE
}

val FormFactor.isPhone get() = this.widthType == WidthType.COMPACT
val FormFactor.isLarge get() = this.widthType != WidthType.COMPACT && this.heightType != HeightType.COMPACT
val FormFactor.isCompact get() = this.widthType == WidthType.COMPACT || this.heightType == HeightType.COMPACT
val FormFactor.isLandscape get() = this.orientation == ScreenOrientation.LANDSCAPE
val FormFactor.isLargeOrLandscape get() = isLarge || isLandscape
val FormFactor.useBottomBar get() = this.widthType == WidthType.COMPACT
val FormFactor.useNavRail get() = this.widthType == WidthType.MEDIUM
val FormFactor.useDrawer get() = this.widthType == WidthType.EXPANDED
val FormFactor.applyTopBarScroll get() = this.heightType == HeightType.COMPACT

val FormFactor.isCompactHeight get() = this.heightType == HeightType.COMPACT

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun rememberFormFactor(): FormFactor {
    val adaptiveInfo = currentWindowAdaptiveInfo()
    val configuration = LocalConfiguration.current

    return remember(adaptiveInfo) {
        val isCompactWidth = !adaptiveInfo.windowSizeClass
            .isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)

        val isExpandedWidth = adaptiveInfo.windowSizeClass
            .isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)

        val isCompactHeight = !adaptiveInfo.windowSizeClass
            .isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND)

        val isExpandedHeight = adaptiveInfo.windowSizeClass
            .isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_EXPANDED_LOWER_BOUND)

        val widthType = when {
            isCompactWidth -> WidthType.COMPACT
            isExpandedWidth -> WidthType.EXPANDED
            else -> WidthType.MEDIUM
        }

        val heightType = when {
            isCompactHeight  -> HeightType.COMPACT
            isExpandedHeight -> HeightType.EXPANDED
            else -> HeightType.MEDIUM
        }

        val screenOrientation =
            if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                ScreenOrientation.LANDSCAPE
            else
                ScreenOrientation.PORTRAIT

        FormFactor(
            widthType = widthType,
            heightType = heightType,
            orientation = screenOrientation
        ).also{
            println("Formfactor: $it , width ${ configuration.screenWidthDp}, height ${configuration.screenHeightDp} ")
        }
    }
}

