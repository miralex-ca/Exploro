package com.exploramus.app.composables.screens.quizzes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.exploramus.app.design.theme.SectionColor
import com.exploramus.app.design.theme.SectionColorPalette
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizType
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzesSectionType

fun String?.toSectionColor(): SectionColor = when (this?.lowercase()) {
    "af" -> SectionColorPalette.Green
    "eu" -> SectionColorPalette.Blue
    "as" -> SectionColorPalette.DeepOrange
    "na" -> SectionColorPalette.Purple
    "sa" -> SectionColorPalette.Amber
    "oc" -> SectionColorPalette.Cyan
    else -> SectionColorPalette.BlueGrey
}

fun QuizzesSectionType.toSectionColor(continentId: String? = null): SectionColor = when (this) {
    QuizzesSectionType.FAVORITES -> SectionColorPalette.Favorites
    QuizzesSectionType.ALL_COUNTRIES -> SectionColorPalette.AllCountries
    QuizzesSectionType.CONTINENT -> continentId.toSectionColor()
}

fun QuizzesSectionType.getIcon(): ImageVector = when (this) {
    QuizzesSectionType.FAVORITES -> Icons.Default.Star
    QuizzesSectionType.ALL_COUNTRIES -> Icons.Default.Public
    QuizzesSectionType.CONTINENT -> Icons.Default.Map
}

fun QuizType.toSectionColor(): SectionColor = when (this) {
    QuizType.FLASHCARDS -> SectionColorPalette.Purple
    QuizType.TEST_COUNTRY_CAPITAL -> SectionColorPalette.Blue
    QuizType.TEST_FLAG_COUNTRY -> SectionColorPalette.Green
}

fun QuizType.getIcon(): ImageVector = when (this) {
    QuizType.FLASHCARDS -> Icons.Default.Style
    QuizType.TEST_COUNTRY_CAPITAL -> Icons.Default.LocationCity
    QuizType.TEST_FLAG_COUNTRY -> Icons.Default.Flag
}
