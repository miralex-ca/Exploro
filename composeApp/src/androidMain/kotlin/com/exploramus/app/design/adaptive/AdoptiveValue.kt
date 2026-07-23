package com.exploramus.app.design.adaptive

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

typealias Adp = AdaptiveValue<Dp>
typealias AdpH = AdaptiveHeightValue<Dp>
typealias AdSp = AdaptiveValue<TextUnit>
typealias AdBool = AdaptiveValue<Boolean>
typealias AdHeightBool = AdaptiveHeightValue<Boolean>
typealias AdHeightSp = AdaptiveHeightValue<TextUnit>


data class AdaptiveValue<T>(
    val compact: T,
    val medium: T = compact,
    val expanded: T = medium,
)

data class AdaptiveHeightValue<T>(
    val compact: T,
    val medium: T = compact,
    val expandedInCompact: T = medium,
    val expanded: T = medium
)

fun adp(
    compact: Dp,
    medium: Dp = compact,
    expanded: Dp = medium
): Adp = AdaptiveValue(compact, medium, expanded)

fun adph(
    compact: Dp,
    medium: Dp = compact,
    expandedInCompact: Dp = medium,
    expanded: Dp = expandedInCompact
): AdpH = AdaptiveHeightValue(compact, medium, expandedInCompact, expanded)


@Composable
fun <T> AdaptiveValue<T>.value(): T {
    return when (LocalFormFactor.current.widthType) {
        WidthType.COMPACT -> compact
        WidthType.MEDIUM -> medium
        WidthType.EXPANDED -> expanded
    }
}

@Composable
fun <T> AdaptiveHeightValue<T>.value(): T {
    val formFactor = LocalFormFactor.current
    return when (formFactor.heightType) {
        HeightType.COMPACT -> compact
        HeightType.MEDIUM -> medium
        HeightType.EXPANDED if formFactor.isCompact -> expandedInCompact
        HeightType.EXPANDED -> expanded
    }
}

data class AdaptiveSizeValue<T>(
    val compactCompact: T, // small screen
    val compactMedium: T = compactCompact, // small phone
    val compactExpanded: T = compactCompact, // phone portrait

    val mediumCompact: T = compactMedium, // small phone landscape
    val mediumMedium: T = compactMedium, // foldable
    val mediumExpanded: T = mediumMedium, // tablet portrait


    val expandedCompact: T = mediumMedium, // phone landscape
    val expandedMedium: T = mediumExpanded, // tablet landscape

    val largeMediumExpanded: T = mediumExpanded, // largest tablet, desktop
    val largeExpandedMedium: T = expandedMedium, // large tablet landscape

    val expandedExpanded: T = expandedMedium // largest tablet, desktop
)

@Composable
fun <T> AdaptiveSizeValue<T>.value(): T {
    return when (LocalFormFactor.current.sizeBucket) {
        SizeBucket.CompactCompact -> compactCompact
        SizeBucket.CompactMedium -> compactMedium
        SizeBucket.CompactExpanded -> compactExpanded
        SizeBucket.MediumCompact -> mediumCompact
        SizeBucket.MediumMedium -> mediumMedium
        SizeBucket.MediumExpanded -> mediumExpanded
        SizeBucket.ExpandedCompact -> expandedCompact
        SizeBucket.ExpandedMedium -> expandedMedium
        SizeBucket.ExpandedExpanded -> expandedExpanded
        SizeBucket.LargeMediumExpanded -> largeMediumExpanded
        SizeBucket.LargeExpandedMedium -> largeExpandedMedium
        SizeBucket.LargeExpandedExpanded -> expandedExpanded
    }
}

fun adpSize(
    compact: Dp,
    medium: Dp = compact,
    expanded: Dp = medium,
) = AdaptiveSizeValue( compactCompact = compact, mediumMedium = medium, mediumExpanded = expanded)

fun adpSizeForHeight(
    compact: Dp,
    medium: Dp = compact,
    expanded: Dp = medium,
): AdaptiveSizeValue<Dp> =
    AdaptiveSizeValue(
        compactCompact = compact,
        compactMedium = medium,
        compactExpanded = expanded,

        mediumCompact = compact,
        mediumMedium = medium,
        mediumExpanded = expanded,

        expandedCompact = compact,
        expandedMedium = medium,
        expandedExpanded = expanded
    )

fun adpSizeForFormat(
    phone: Dp,
    phoneLandscape: Dp = phone,
    largePhone: Dp = phone,
    largePhoneLandscape: Dp = phoneLandscape,
    foldable: Dp = largePhone,
    tablet: Dp = largePhone,
    tabletLandscape: Dp = tablet,
    largeTablet: Dp = tablet,
    largeTabletLandscape: Dp = tabletLandscape,
): AdaptiveSizeValue<Dp> =
    AdaptiveSizeValue(
        compactCompact = phone,
        compactMedium = phone,
        compactExpanded = largePhone,

        mediumCompact = phoneLandscape,
        mediumMedium = foldable,
        mediumExpanded = tablet,
        largeMediumExpanded = largeTablet,

        expandedCompact = largePhoneLandscape,
        expandedMedium = tabletLandscape,
        largeExpandedMedium = largeTabletLandscape,
        expandedExpanded = largeTabletLandscape
    )