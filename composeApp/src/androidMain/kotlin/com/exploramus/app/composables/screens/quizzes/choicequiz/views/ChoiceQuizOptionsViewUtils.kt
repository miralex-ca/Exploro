package com.exploramus.app.composables.screens.quizzes.choicequiz.views

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.exploramus.app.design.theme.AppColorPalette
import com.exploramus.app.design.theme.AppColorSet
import com.exploramus.app.design.theme.appColors

@Composable
internal fun getOptionBorderColor(isSelected: Boolean, isCorrect: Boolean, isSubmitted: Boolean): Color {
    return when {
        !isSubmitted -> {
            if (isSelected) AppColorPalette.Blue.icon()
            else MaterialTheme.appColors.cardBorder.copy(alpha = 0.5f)
        }
        isCorrect -> AppColorPalette.GreenCorrect.icon()
        isSelected -> AppColorPalette.RedIncorrect.icon()
        else -> MaterialTheme.appColors.cardBorder.copy(alpha = 0.3f)
    }
}

@Composable
internal fun getOptionContentColor(isSelected: Boolean, isCorrect: Boolean, isSubmitted: Boolean): Color {
    return when {
        !isSubmitted -> {
            if (isSelected) AppColorPalette.Blue.text()
            else MaterialTheme.colorScheme.onSurface
        }
        isCorrect -> AppColorPalette.GreenCorrect.onIcon()
        isSelected -> AppColorPalette.RedIncorrect.onIcon()
        else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
    }
}

@Composable
internal fun getOptionBackgroundColor(isSelected: Boolean, isCorrect: Boolean, isSubmitted: Boolean): Color {
    return when {
        !isSubmitted -> Color.Transparent
        isCorrect -> AppColorPalette.GreenCorrect.icon()
        isSelected -> AppColorPalette.RedIncorrect.icon()
        else -> Color.Transparent
    }
}

internal fun getOptionBorderWidth(isSelected: Boolean, isCorrect: Boolean, isSubmitted: Boolean, isGrid: Boolean): Dp {
    val isHighlighted = isSelected || (isSubmitted && isCorrect)
    return when {
        isGrid && isHighlighted -> 4.dp
        isHighlighted -> 2.dp
        else -> 2.dp
    }
}

internal fun getOptionAlpha(isSelected: Boolean, isCorrect: Boolean, isSubmitted: Boolean): Float {
    return if (isSubmitted && !isSelected && !isCorrect) 0.7f else 1f
}

val AppColorPalette.GreenCorrect: AppColorSet
    get() = AppColorSet(
        iconLight = Color(0xFF2AB020),
        iconDark = Color(0xFF209319),
        backgroundLight = Color(0xFFE8F8E9),
        backgroundDark = Color(0xFF46DD3A).copy(alpha = 0.2f),
        textLight = Color(0xFF269B1E),
        textDark = Color(0xFFAFFAB0),
        onIconLight = Color.White,
        onIconDark = Color.White
    )

val AppColorPalette.RedIncorrect: AppColorSet
    get() = AppColorSet(
        iconLight = Color(0xFFF53B36),
        iconDark = Color(0xFFD53330),
        backgroundLight = Color(0xFFFFEBEE),
        backgroundDark = Color(0xFFEF5350).copy(alpha = 0.2f),
        textLight = Color(0xFFD32F2F),
        textDark = Color(0xFFFF8A80),
        onIconLight = Color.White,
        onIconDark = Color.White
    )
