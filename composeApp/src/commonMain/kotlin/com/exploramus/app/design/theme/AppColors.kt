package com.exploramus.app.design.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val MaterialTheme.appColors: AppColors
    @Composable
    @ReadOnlyComposable
    get() = LocalAppColors.current

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
    val quiz: QuizColors

) {
    data class QuizColors(
        val optionBg: Color = Color.LightGray.copy(alpha = 0.1f),
        val optionBorder: Color = Color.LightGray.copy(alpha = 0.5f),
        val optionBorderInactive: Color = Color.LightGray.copy(alpha = 0.3f),
        val metricBorder: Color = Color.LightGray.copy(alpha = 0.5f),
    )
}

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
    quiz = AppColors.QuizColors(
        optionBg = onBlueSurface.copy(alpha = 0.01f),
        optionBorder = onBlueSurface.copy(alpha = 0.2f),
        optionBorderInactive = onBlueSurface.copy(alpha = 0.05f),
        metricBorder = onBlueSurface.copy(alpha = 0.4f),
    )
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
    quiz = AppColors.QuizColors(
        optionBg = OnDarkSurface.copy(alpha = 0.03f),
        optionBorder = OnDarkSurface.copy(alpha = 0.1f),
        optionBorderInactive = OnDarkSurface.copy(alpha = 0.05f),
        metricBorder = OnDarkSurface.copy(alpha = 0.4f),
    )
)
