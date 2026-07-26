package com.exploramus.shared.viewmodel.utils

import com.exploramus.core.models.ChoiceQuizStudyTarget
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizType
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzesSectionType

object QuizIdBuilder {
    fun build(
        sectionId: String,
        sectionType: QuizzesSectionType,
        quizType: QuizType,
        studyTarget: ChoiceQuizStudyTarget? = null
    ): String {
        val prefix = when (sectionType) {
            QuizzesSectionType.FAVORITES -> "favorites"
            QuizzesSectionType.ALL_COUNTRIES -> "all"
            QuizzesSectionType.CONTINENT -> sectionId.lowercase()
        }

        val typeSuffix = when (quizType) {
            QuizType.FLASHCARDS -> "flashcards"
            QuizType.CHOICE_QUIZ_PRIMARY_SECONDARY,
            QuizType.CHOICE_QUIZ_IMAGE_PRIMARY -> {
                val target = studyTarget ?: if (quizType == QuizType.CHOICE_QUIZ_IMAGE_PRIMARY) 
                    ChoiceQuizStudyTarget.IMAGE_PRIMARY else ChoiceQuizStudyTarget.PRIMARY_SECONDARY
                
                when (target) {
                    ChoiceQuizStudyTarget.PRIMARY_SECONDARY -> "choice_ps"
                    ChoiceQuizStudyTarget.SECONDARY_PRIMARY -> "choice_sp"
                    ChoiceQuizStudyTarget.IMAGE_PRIMARY -> "choice_ip"
                    ChoiceQuizStudyTarget.PRIMARY_IMAGE -> "choice_pi"
                }
            }
        }

        return "${prefix}_$typeSuffix"
    }
}
