package com.exploramus.app.design.adaptive

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalWindowInfo
import platform.UIKit.UIDevice
import platform.UIKit.UIUserInterfaceIdiomPad

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun rememberFormFactor(): FormFactor {
    val windowInfo = LocalWindowInfo.current
    val containerSize = windowInfo.containerSize
    
    return remember(containerSize) {
        val isPad = UIDevice.currentDevice.userInterfaceIdiom == UIUserInterfaceIdiomPad
        
        val w = containerSize.width
        val h = containerSize.height
        
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
        )
    }
}
