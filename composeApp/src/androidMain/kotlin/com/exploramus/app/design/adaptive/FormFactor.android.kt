package com.exploramus.app.design.adaptive

import android.annotation.SuppressLint
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.window.core.layout.WindowSizeClass
import com.exploramus.core.common.logging.Log
import kotlin.math.min

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
actual fun rememberFormFactor(): FormFactor {
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
