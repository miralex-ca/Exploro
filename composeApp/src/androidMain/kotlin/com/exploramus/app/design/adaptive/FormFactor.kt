package com.exploramus.app.design.adaptive

import android.annotation.SuppressLint
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.window.core.layout.WindowSizeClass
import com.exploramus.core.common.logging.Log
import kotlin.math.min

val LocalFormFactor = staticCompositionLocalOf<FormFactor> {
    error("LocalFormFactor not provided — wrap with CompositionLocalProvider at root")
}

data class FormFactor(
    val widthType: WidthType,
    val heightType: HeightType,
    val orientation: ScreenOrientation,
    val isLargeTablet: Boolean,
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

enum class SizeBucket {
    CompactCompact,
    CompactMedium,
    CompactExpanded,

    MediumCompact,
    MediumMedium,
    MediumExpanded,

    ExpandedCompact,
    ExpandedMedium,
    ExpandedExpanded,

    LargeExpandedMedium,
    LargeMediumExpanded,
    LargeExpandedExpanded,
}

val FormFactor.isPhone get() = this.widthType == WidthType.COMPACT
val FormFactor.isLarge get() = this.widthType != WidthType.COMPACT && this.heightType != HeightType.COMPACT
val FormFactor.isCompact get() = this.widthType == WidthType.COMPACT || this.heightType == HeightType.COMPACT
val FormFactor.isLandscape get() = this.orientation == ScreenOrientation.LANDSCAPE
val FormFactor.isLargeOrLandscape get() = isLarge || isLandscape
val FormFactor.useBottomBar get() = this.widthType == WidthType.COMPACT
val FormFactor.useNavRail get() = widthType == WidthType.MEDIUM || (widthType == WidthType.EXPANDED && isCompactHeight)
val FormFactor.useDrawer get() = this.widthType == WidthType.EXPANDED && !isCompactHeight
val FormFactor.isCompactHeight get() = this.heightType == HeightType.COMPACT
val FormFactor.sizeBucket: SizeBucket
    get() = when (widthType) {
        WidthType.COMPACT -> when (heightType) {
            HeightType.COMPACT -> SizeBucket.CompactCompact
            HeightType.MEDIUM -> SizeBucket.CompactMedium
            HeightType.EXPANDED -> SizeBucket.CompactExpanded
        }

        WidthType.MEDIUM -> when (heightType) {
            HeightType.COMPACT -> SizeBucket.MediumCompact
            HeightType.MEDIUM -> SizeBucket.MediumMedium
            HeightType.EXPANDED if isLargeTablet -> SizeBucket.LargeMediumExpanded
            HeightType.EXPANDED -> SizeBucket.MediumExpanded
        }

        WidthType.EXPANDED -> when (heightType) {
            HeightType.COMPACT -> SizeBucket.ExpandedCompact
            HeightType.MEDIUM if isLargeTablet -> SizeBucket.LargeExpandedMedium
            HeightType.MEDIUM -> SizeBucket.ExpandedMedium
            HeightType.EXPANDED if isLargeTablet -> SizeBucket.LargeExpandedExpanded
            HeightType.EXPANDED -> SizeBucket.ExpandedExpanded
        }
    }

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

        val isLargeTablet = configuration.screenWidthDp > 1200 || configuration.screenHeightDp > 1100

        val smallestSizeDp = min(configuration.screenWidthDp, configuration.screenHeightDp)
        val isCompactDSize = smallestSizeDp >= 550

        val widthType = when {
            isCompactWidth -> WidthType.COMPACT
            isExpandedWidth -> WidthType.EXPANDED
            else -> WidthType.MEDIUM
        }

        val heightType = when {
            isCompactHeight  -> HeightType.COMPACT
            !isCompactDSize && widthType == WidthType.EXPANDED -> HeightType.COMPACT
            isExpandedHeight -> HeightType.EXPANDED
            else -> HeightType.MEDIUM
        }

        val screenOrientation =
            if (configuration.screenWidthDp > configuration.screenHeightDp)
                ScreenOrientation.LANDSCAPE
            else
                ScreenOrientation.PORTRAIT



        FormFactor(
            widthType = widthType,
            heightType = heightType,
            orientation = screenOrientation,
            isLargeTablet = isLargeTablet,
        ).also{
            Log.d("Formfactor: $it, size: ${it.sizeBucket} , width ${ configuration.screenWidthDp}, height ${configuration.screenHeightDp} ")
        }
    }
}

