package com.exploramus.app.design.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


val LocalAppColors = staticCompositionLocalOf<AppColors> {
    error("No AppColors provided")
}

data class AppColors(
    val isDark: Boolean,
    val topBarContainer: Color,
    val onTopBarContainer: Color,
    val caretColor: Color,
    val detailHederWrapper: Color,
    val favorite: Color,
    val destructive: Color,
    val cardBorder: Color,
    val pagerIndicatorBorder: Color,
    val quizOptionBg: Color,
)

val LightAppColors = AppColors(
    isDark = false,
    topBarContainer = TopBarContaineColor,
    onTopBarContainer = OnTopBarContainer,
    detailHederWrapper = Color.Black.copy(alpha = 0.05f),
    caretColor = CaretColor,
    cardBorder = CardBorderLight,
    favorite = FavoriteColor,
    destructive = DestructiveColor,
    pagerIndicatorBorder = CardBorderLight.copy(alpha = 0.0f),
    quizOptionBg = Color(0xFFCED6E7).copy(alpha = 0.2f),
)

val DarkAppColors = AppColors(
    isDark = true,
    topBarContainer = TopBarContainerDark,
    onTopBarContainer = OnTopBarContainerDark,
    detailHederWrapper = Color.White.copy(alpha = 0.15f),
    caretColor = DarkCaretColor,
    favorite = FavoriteColor,
    destructive = DestructiveColor,
    cardBorder = CardBorderDark,
    pagerIndicatorBorder = CardBorderDark,
    quizOptionBg = Color(0xFFCED6E7).copy(alpha = 0.2f),
)
