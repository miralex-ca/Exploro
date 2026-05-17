package com.muralex.myapp.theme

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
    val cardBorder: Color
)

val LightAppColors = AppColors(
    topBarContainer = TopBarContainer,
    onTopBarContainer = OnTopBarContainer,
    detailHederWrapper = Color.Black.copy(alpha = 0.05f),
    caretColor = CaretColor,
    cardBorder = CardBorderLight,
    favorite = FavoriteColor,
    destructive = DestructiveColor,
)

val DarkAppColors = AppColors(
    topBarContainer = TopBarContainerDark,
    onTopBarContainer = OnTopBarContainerDark,
    detailHederWrapper = Color.White.copy(alpha = 0.15f),
    caretColor = DarkCaretColor,
    favorite = FavoriteColor,
    destructive = DestructiveColor,
    cardBorder = CardBorderDark
)