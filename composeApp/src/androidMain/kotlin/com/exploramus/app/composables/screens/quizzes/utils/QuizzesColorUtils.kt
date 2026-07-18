package com.exploramus.app.composables.screens.quizzes.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.material.icons.outlined.PieChart
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.SentimentDissatisfied
import androidx.compose.material.icons.rounded.SentimentNeutral
import androidx.compose.material.icons.rounded.SentimentSatisfied
import androidx.compose.material.icons.rounded.SentimentVeryDissatisfied
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.exploramus.app.R
import com.exploramus.app.design.theme.AppColorPalette
import com.exploramus.app.design.theme.AppColorSet
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizType
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzesSectionType

object QuizColorPalette {
    val Favorites = AppColorPalette.Amber
    val AllCountries = AppColorPalette.Blue
    val AllContinents = AppColorPalette.Pink
    val Quizzes = AppColorPalette.BlueGrey
}

fun String?.toAppColorSet(): AppColorSet = when (this?.lowercase()) {
    "af" -> AppColorPalette.Green
    "eu" -> AppColorPalette.Blue
    "as" -> AppColorPalette.DeepOrange
    "na" -> AppColorPalette.Purple
    "sa" -> AppColorPalette.Amber
    "oc" -> AppColorPalette.Cyan
    else -> AppColorPalette.BlueGrey
}

fun QuizzesSectionType.toAppColorSet(continentId: String? = null): AppColorSet = when (this) {
    QuizzesSectionType.FAVORITES -> QuizColorPalette.Favorites
    QuizzesSectionType.ALL_COUNTRIES -> QuizColorPalette.AllCountries
    QuizzesSectionType.CONTINENT if (continentId == null) -> QuizColorPalette.AllContinents
    QuizzesSectionType.CONTINENT -> continentId.toAppColorSet()
}

@Composable
fun QuizzesSectionType.getIcon(): ImageVector = when (this) {
    QuizzesSectionType.FAVORITES -> Icons.Default.Star
    QuizzesSectionType.ALL_COUNTRIES -> Icons.Default.Public
    QuizzesSectionType.CONTINENT -> Icons.Default.Map
}

fun QuizType.toAppColorSet(): AppColorSet = when (this) {
    QuizType.FLASHCARDS -> AppColorPalette.MintGreen
    QuizType.CHOICE_QUIZ_PRIMARY_SECONDARY -> AppColorPalette.SkyBlue
    QuizType.CHOICE_QUIZ_IMAGE_PRIMARY -> AppColorPalette.SkyCyan
}

@Composable
fun QuizType.getIcon(): ImageVector = when (this) {
    QuizType.FLASHCARDS -> ImageVector.vectorResource(R.drawable.cards_outline)
    QuizType.CHOICE_QUIZ_PRIMARY_SECONDARY -> Icons.Outlined.LocationCity
    QuizType.CHOICE_QUIZ_IMAGE_PRIMARY -> Icons.Outlined.Flag
}

data class QuizResultGrade(
    val title: String,
    val colorSet: AppColorSet,
    val icon: ImageVector,
    val scoreIcon: ImageVector = Icons.Outlined.PieChart
)

fun getQuizResultGrade(scorePercentage: Int): QuizResultGrade = when {
    scorePercentage >= 96 -> QuizResultGrade("Brilliant Work!", AppColorPalette.BrightGreen, Icons.Rounded.EmojiEvents)
    scorePercentage >= 80 -> QuizResultGrade("Excellent Effort!", AppColorPalette.Jade, Icons.Rounded.EmojiEvents)
    scorePercentage >= 50 -> QuizResultGrade("Good Progress!", AppColorPalette.Blue, Icons.Rounded.SentimentSatisfied)
    scorePercentage >= 30 -> QuizResultGrade("Getting There!", AppColorPalette.DeepOrange, Icons.Rounded.SentimentNeutral)
    scorePercentage >= 1 -> QuizResultGrade("Keep Practicing!", AppColorPalette.Red, Icons.Rounded.SentimentDissatisfied)
    else -> QuizResultGrade("Start Learning!", AppColorPalette.BlueGrey, Icons.Rounded.SentimentVeryDissatisfied)
}
