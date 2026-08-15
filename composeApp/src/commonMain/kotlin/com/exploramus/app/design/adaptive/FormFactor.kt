package com.exploramus.app.design.adaptive

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf

val LocalFormFactor = staticCompositionLocalOf<FormFactor> {
    error("LocalFormFactor not provided — wrap with CompositionLocalProvider at root")
}

@Composable
expect fun rememberFormFactor(): FormFactor

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
