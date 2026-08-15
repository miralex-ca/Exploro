package com.exploramus.app.composables.screens.quizzes.utils

import androidx.compose.runtime.Composable
import com.exploramus.app.resources.Strings
import com.exploramus.core.models.ChoiceQuizStudyTarget
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.ChoiceQuizQuestionState
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizState
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizType
import com.exploramus.shared.viewmodel.utils.toQuizDateTimeParts

@Composable
fun ChoiceQuizStudyTarget.toTitle(): String = when (this) {
    ChoiceQuizStudyTarget.PRIMARY_SECONDARY -> Strings.quizTargetPrimarySecondary
    ChoiceQuizStudyTarget.SECONDARY_PRIMARY -> Strings.quizTargetSecondaryPrimary
    ChoiceQuizStudyTarget.IMAGE_PRIMARY -> Strings.quizTargetImagePrimary
    ChoiceQuizStudyTarget.PRIMARY_IMAGE -> Strings.quizTargetPrimaryImage
}

@Composable
fun QuizState.toTitle(
    psTarget: ChoiceQuizStudyTarget,
    ipTarget: ChoiceQuizStudyTarget
): String = when (quizType) {
    QuizType.FLASHCARDS -> Strings.quizTypeFlashcards
    QuizType.CHOICE_QUIZ_PRIMARY_SECONDARY -> psTarget.toTitle()
    QuizType.CHOICE_QUIZ_IMAGE_PRIMARY -> ipTarget.toTitle()
}

@Composable
fun QuizState.toDescription(): String {
    val result = this.result
    if (result == null || result.totalAnswers == 0 || result.correctAnswers == 0) {
        return when (quizType) {
            QuizType.FLASHCARDS -> Strings.quizDescFlashcards
            QuizType.CHOICE_QUIZ_PRIMARY_SECONDARY -> Strings.quizDescChoicePs
            QuizType.CHOICE_QUIZ_IMAGE_PRIMARY -> Strings.quizDescChoiceIp
        }
    }

    val scorePercent = (result.score * 100).toInt()
    val scoreText = Strings.quizResultLastScore(scorePercent, result.correctAnswers, result.totalAnswers)
    
    val dateTimeParts = result.completedAt.toQuizDateTimeParts()
    val dateText = if (dateTimeParts != null) {
        Strings.quizResultCompletedAt(Strings.quizResultDateTimeAt(dateTimeParts.first, dateTimeParts.second))
    } else {
        ""
    }

    return "$scoreText\n$dateText"
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
