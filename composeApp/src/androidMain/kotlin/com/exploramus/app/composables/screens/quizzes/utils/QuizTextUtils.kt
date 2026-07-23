package com.exploramus.app.composables.screens.quizzes.utils

import androidx.compose.runtime.Composable
import com.exploramus.app.resources.Strings
import com.exploramus.core.models.ChoiceQuizStudyTarget
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizQuestionState

@Composable
fun ChoiceQuizStudyTarget.toTitle(): String = when (this) {
    ChoiceQuizStudyTarget.PRIMARY_SECONDARY -> Strings.quizTargetPrimarySecondary
    ChoiceQuizStudyTarget.SECONDARY_PRIMARY -> Strings.quizTargetSecondaryPrimary
    ChoiceQuizStudyTarget.IMAGE_PRIMARY -> Strings.quizTargetImagePrimary
    ChoiceQuizStudyTarget.PRIMARY_IMAGE -> Strings.quizTargetPrimaryImage
}

@Composable
fun ChoiceQuizStudyTarget.toPrompt(): String? = when (this) {
    ChoiceQuizStudyTarget.PRIMARY_SECONDARY -> Strings.choiceQuizPromptPrimarySecondary
    ChoiceQuizStudyTarget.SECONDARY_PRIMARY -> Strings.choiceQuizPromptSecondaryPrimary
    ChoiceQuizStudyTarget.IMAGE_PRIMARY -> null
    ChoiceQuizStudyTarget.PRIMARY_IMAGE -> Strings.choiceQuizPromptPrimaryImage
}

@Composable
fun ChoiceQuizQuestionState.toPrompt(): String? = studyTarget.toPrompt()
