package com.exploramus.app.composables.screens.quizzes.choicequiz.views

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.exploramus.app.design.theme.AppColorPalette
import com.exploramus.app.design.theme.appColors

@Composable
internal fun getOptionBorderColor(isSelected: Boolean, isCorrect: Boolean, isSubmitted: Boolean): Color {
    return when {
        !isSubmitted -> {
            if (isSelected) AppColorPalette.Blue.icon()
            else MaterialTheme.appColors.cardBorder.copy(alpha = 0.5f)
        }
        isCorrect -> AppColorPalette.Green.icon()
        isSelected -> AppColorPalette.Red.icon()
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
        isCorrect -> AppColorPalette.Green.onIcon()
        isSelected -> AppColorPalette.Red.onIcon()
        else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
    }
}

@Composable
internal fun getOptionBackgroundColor(isSelected: Boolean, isCorrect: Boolean, isSubmitted: Boolean): Color {
    return when {
        !isSubmitted -> Color.Transparent
        isCorrect -> AppColorPalette.Green.icon()
        isSelected -> AppColorPalette.Red.icon()
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
