package com.muralex.exploramus.design.adaptive

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
    expanded: Dp = medium
): AdpH = AdaptiveHeightValue(compact, medium, expanded)


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
    return when (LocalFormFactor.current.heightType) {
        HeightType.COMPACT -> compact
        HeightType.MEDIUM -> medium
        HeightType.EXPANDED -> expanded
    }
}

