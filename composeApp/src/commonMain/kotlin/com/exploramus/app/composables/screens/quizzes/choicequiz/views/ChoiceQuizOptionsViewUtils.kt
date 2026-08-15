package com.exploramus.app.composables.screens.quizzes.choicequiz.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.RocketLaunch
import androidx.compose.material.icons.rounded.SentimentSatisfied
import androidx.compose.material.icons.rounded.WorkspacePremium
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.exploramus.app.composables.screens.quizzes.utils.QuizResultGrade
import com.exploramus.app.design.theme.AppColorPalette
import com.exploramus.app.design.theme.AppColorSet
import com.exploramus.app.design.theme.appColors
import com.exploramus.app.resources.painterResource

@Composable
internal fun getOptionBorderColor(isSelected: Boolean, isCorrect: Boolean, isSubmitted: Boolean): Color {
    return when {
        !isSubmitted -> {
            if (isSelected) AppColorPalette.Blue.icon()
            else MaterialTheme.appColors.quiz.optionBorder
        }
        isCorrect -> AppColorPalette.GreenCorrect.icon()
        isSelected -> AppColorPalette.RedIncorrect.icon()
        else -> MaterialTheme.appColors.cardBorder.copy(alpha = 0.6f)
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
        else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    }
}

@Composable
internal fun getOptionBackgroundColor(isSelected: Boolean, isCorrect: Boolean, isSubmitted: Boolean): Color {
    return when {
        !isSubmitted -> MaterialTheme.appColors.quiz.optionBg
        isCorrect -> AppColorPalette.GreenCorrect.icon()
        isSelected -> AppColorPalette.RedIncorrect.icon()
        else -> MaterialTheme.appColors.quiz.optionBg
    }
}

internal fun getOptionBorderWidth(isSelected: Boolean, isCorrect: Boolean, isSubmitted: Boolean, isGrid: Boolean): Dp {
    val isHighlighted = isSelected || (isSubmitted && isCorrect)
    return when {
        isGrid && isHighlighted -> 4.dp
        isHighlighted -> 2.dp
        else -> 1.dp
    }
}

internal fun getOptionAlpha(isSelected: Boolean, isCorrect: Boolean, isSubmitted: Boolean): Float {
    return if (isSubmitted && !isSelected && !isCorrect) 0.7f else 1f
}

@Composable
internal fun getChoiceQuizIconPainter(isCorrect: Boolean): Painter {
    return painterResource(if (isCorrect) "check_bold" else "close_bold")
}

fun getQuizResultGrade(scorePercentage: Int): QuizResultGrade = when {
    scorePercentage >= 96 -> QuizResultGrade("Brilliant Work!", AppColorPalette.HighestGrade, Icons.Rounded.WorkspacePremium)
    scorePercentage >= 80 -> QuizResultGrade("Excellent Effort!", AppColorPalette.HighGrade, Icons.Rounded.EmojiEvents)
    scorePercentage >= 50 -> QuizResultGrade("Good job!", AppColorPalette.NormalGrade, Icons.Rounded.SentimentSatisfied)
    scorePercentage >= 30 -> QuizResultGrade("Making Progress!", AppColorPalette.LowGrade, Icons.Outlined.ThumbUp)
    scorePercentage >= 1 -> QuizResultGrade("Keep Practicing!", AppColorPalette.LowestGrade, Icons.AutoMirrored.Rounded.TrendingUp)
    else -> QuizResultGrade("Start Learning!", AppColorPalette.NoGrade, Icons.Rounded.RocketLaunch)
}


val AppColorPalette.GreenCorrect: AppColorSet
    get() = AppColorSet(
        iconLight = Color(0xFF3DAB35),
        iconDark = Color(0xFF209319),
        backgroundLight = Color(0xFFE8F8E9),
        backgroundDark = Color(0xFF4ED543).copy(alpha = 0.2f),
        textLight = Color(0xFF269B1E),
        textDark = Color(0xFFAFFAB0),
        onIconLight = Color.White,
        onIconDark = Color.White
    )

val AppColorPalette.RedIncorrect: AppColorSet
    get() = AppColorSet(
        iconLight = Color(0xFFEC4642),
        iconDark = Color(0xFFD53330),
        backgroundLight = Color(0xFFFFEBEE),
        backgroundDark = Color(0xFFEF5350).copy(alpha = 0.2f),
        textLight = Color(0xFFD32F2F),
        textDark = Color(0xFFFF8A80),
        onIconLight = Color.White,
        onIconDark = Color.White
    )

val AppColorPalette.HighestGrade: AppColorSet
    get() = AppColorSet(
        iconLight = Color(0xFF19D401),
        iconDark = Color(0xFF5CEF49),
        backgroundLight = Color(0xFFC7F6C9),
        backgroundDark = Color(0xFF19D401).copy(alpha = 0.2f),
        textLight = Color(0xFF30CC1C),
        textDark = Color(0xFF93F184),
        onIconLight = Color.White,
        onIconDark = Color.Black
    )

val AppColorPalette.HighGrade: AppColorSet
    get() = AppColorSet(
        iconLight = Color(0xFF09BD6A),
        iconDark = Color(0xFFA0F5C9),
        backgroundLight = Color(0xFF22E369).copy(alpha = 0.2f),
        backgroundDark = Color(0xFF11CB5B).copy(alpha = 0.2f),
        textLight = Color(0xFF0FB267),
        textDark = Color(0xFFA0F5C9),
        onIconLight = Color.White,
        onIconDark = Color.White
    )

val AppColorPalette.NormalGrade: AppColorSet
    get() = AppColorSet(
        iconLight = Color(0xFF2BABEC),
        iconDark = Color(0xFF80D7FD),
        backgroundLight = Color(0xFF71CDF8).copy(alpha = 0.2f),
        backgroundDark = Color(0xFF4DC4F8).copy(alpha = 0.25f),
        textLight = Color(0xFF1AA2E8),
        textDark = Color(0xFF95DCFC),
        onIconLight = Color.White,
        onIconDark = Color.White
    )

val AppColorPalette.LowGrade: AppColorSet
    get() = AppColorSet(
        iconLight = Color(0xFFFC764C),
        iconDark = Color(0xFFFF7043),
        backgroundLight = Color(0xFFFBE9E7),
        backgroundDark = Color(0xFFFF7043).copy(alpha = 0.2f),
        textLight = Color(0xFFFF7043),
        textDark = Color(0xFFFC8964),
        onIconLight = Color.White,
        onIconDark = Color.White
    )

val AppColorPalette.LowestGrade: AppColorSet
    get() = AppColorSet(
        iconLight = Color(0xFFE53935),
        iconDark = Color(0xFFFA6866),
        backgroundLight = Color(0xFFFFEBEE),
        backgroundDark = Color(0xFFF35A57).copy(alpha = 0.2f),
        textLight = Color(0xFFFA3838),
        textDark = Color(0xFFFA8683),
        onIconLight = Color.White,
        onIconDark = Color.White
    )

val AppColorPalette.NoGrade: AppColorSet
    get() = AppColorSet(
        iconLight = Color(0xFFF19B06),
        iconDark = Color(0xFFFDDD3F),
        backgroundLight = Color(0xFFFAC151).copy(alpha = 0.2f),
        backgroundDark = Color(0xFFFBC02D).copy(alpha = 0.2f),
        textLight = Color(0xFFF59C02),
        textDark = Color(0xFFFFE254),
        onIconLight = Color.White,
        onIconDark = Color(0xFF694301)
    )