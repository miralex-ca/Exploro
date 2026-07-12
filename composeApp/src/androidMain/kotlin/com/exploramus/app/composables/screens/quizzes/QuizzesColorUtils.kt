package com.exploramus.app.composables.screens.quizzes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.LocationCity
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
    QuizType.TEST_COUNTRY_CAPITAL -> AppColorPalette.SkyBlue
    QuizType.TEST_FLAG_COUNTRY -> AppColorPalette.SkyCyan
}

@Composable
fun QuizType.getIcon(): ImageVector = when (this) {
    QuizType.FLASHCARDS -> ImageVector.vectorResource(R.drawable.cards_outline)
    QuizType.TEST_COUNTRY_CAPITAL -> Icons.Outlined.LocationCity
    QuizType.TEST_FLAG_COUNTRY -> Icons.Outlined.Flag
}
