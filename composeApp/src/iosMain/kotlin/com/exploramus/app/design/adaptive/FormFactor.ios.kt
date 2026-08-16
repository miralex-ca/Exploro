package com.exploramus.app.design.adaptive

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import com.exploramus.core.common.logging.Log
import platform.UIKit.UIDevice
import platform.UIKit.UIUserInterfaceIdiomPad

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun rememberFormFactor(): FormFactor {
    val windowInfo = LocalWindowInfo.current
    val containerSize = windowInfo.containerSize
    val density = LocalDensity.current
    
    return remember(containerSize, density) {
        val isPad = UIDevice.currentDevice.userInterfaceIdiom == UIUserInterfaceIdiomPad
        
        val w = with(density) { containerSize.width.toDp().value }
        val h = with(density) { containerSize.height.toDp().value }
        
        val widthType = when {
            w < 600 -> WidthType.COMPACT
            w < 840 -> WidthType.MEDIUM
            else -> WidthType.EXPANDED
        }
        
        val heightType = when {
            h < 480 -> HeightType.COMPACT
            h < 900 -> HeightType.MEDIUM
            else -> HeightType.EXPANDED
        }
        
        val orientation = if (w > h) ScreenOrientation.LANDSCAPE else ScreenOrientation.PORTRAIT
        
        FormFactor(
            widthType = widthType,
            heightType = heightType,
            orientation = orientation,
            isLargeTablet = isPad && (w > 1000 || h > 1000)
        ).also {
            Log.d("FormFactor: $it, width: $w, height: $h")
        }
    }
}
