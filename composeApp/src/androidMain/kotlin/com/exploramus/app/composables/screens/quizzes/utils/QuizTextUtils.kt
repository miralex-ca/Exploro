package com.exploramus.app.composables.screens.quizzes.utils

import androidx.compose.runtime.Composable
import com.exploramus.app.resources.Strings
import com.exploramus.core.models.ChoiceQuizStudyTarget

@Composable
fun ChoiceQuizStudyTarget.toTitle(): String = when (this) {
    ChoiceQuizStudyTarget.PRIMARY_SECONDARY -> Strings.quizTargetPrimarySecondary
    ChoiceQuizStudyTarget.SECONDARY_PRIMARY -> Strings.quizTargetSecondaryPrimary
    ChoiceQuizStudyTarget.IMAGE_PRIMARY -> Strings.quizTargetImagePrimary
    ChoiceQuizStudyTarget.PRIMARY_IMAGE -> Strings.quizTargetPrimaryImage
}
