package com.exploramus.app.design.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


val LocalAppColors = staticCompositionLocalOf<AppColors> {
    error("No AppColors provided")
}

data class AppColors(
    val topBarContainer: Color,
    val onTopBarContainer: Color,
    val caretColor: Color,
    val detailHederWrapper: Color,
    val favorite: Color,
    val destructive: Color,
    val cardBorder: Color,
    val pagerIndicatorBorder: Color
)

val LightAppColors = AppColors(
    topBarContainer = TopBarContaineColor,
    onTopBarContainer = OnTopBarContainer,
    detailHederWrapper = Color.Black.copy(alpha = 0.05f),
    caretColor = CaretColor,
    cardBorder = CardBorderLight,
    favorite = FavoriteColor,
    destructive = DestructiveColor,
    pagerIndicatorBorder = CardBorderLight.copy(alpha = 0.0f),
)

val DarkAppColors = AppColors(
    topBarContainer = TopBarContainerDark,
    onTopBarContainer = OnTopBarContainerDark,
    detailHederWrapper = Color.White.copy(alpha = 0.15f),
    caretColor = DarkCaretColor,
    favorite = FavoriteColor,
    destructive = DestructiveColor,
    cardBorder = CardBorderDark,
    pagerIndicatorBorder = CardBorderDark
)