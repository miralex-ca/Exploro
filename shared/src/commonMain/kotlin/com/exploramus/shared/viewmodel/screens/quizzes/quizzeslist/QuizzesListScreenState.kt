package com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist

import com.exploramus.core.models.ChoiceQuizConfig
import com.exploramus.core.models.ChoiceQuizStudyTarget
import com.exploramus.core.models.FlashcardConfig
import com.exploramus.shared.viewmodel.core.ScreenState

data class QuizzesListScreenState(
    val isLoading: Boolean = false,
    val sectionInfo: QuizzesSectionHeaderState = QuizzesSectionHeaderState(),
    val quizzes: List<QuizState> = emptyList(),
    val flashcardConfig: FlashcardConfig? = null,
    val choiceQuizConfig: ChoiceQuizConfig? = null,
    val choiceQuizType: QuizType? = null,
    val psTarget: ChoiceQuizStudyTarget = ChoiceQuizStudyTarget.PRIMARY_SECONDARY,
    val ipTarget: ChoiceQuizStudyTarget = ChoiceQuizStudyTarget.IMAGE_PRIMARY,
) : ScreenState

data class QuizzesSectionHeaderState(
    val title: String = "",
    val itemsCount: Int = 0,
    val sectionType: QuizzesSectionType = QuizzesSectionType.ALL_COUNTRIES,
    val continentId: String? = null,
)

enum class QuizzesSectionType {
    FAVORITES,
    ALL_COUNTRIES,
    CONTINENT,
}


object QuizzCaategoryId {
    val FAVORITES = "favorites"
    val ALL_COUNTRIES = "all_countries"
    val CATEGORY = "category"
}

object QuizzIds {
    val FLASHCARDS = "flashcards"
    val CHOICE_QUIZ_PRIMARY_SECONDARY = "choice_quiz_primary_secondary"
    val CHOICE_QUIZ_SECONDARY_PRIMARY = "choice_quiz_secondary_primary"
    val CHOICE_QUIZ_PRIMARY_IMAGE = "choice_quiz_primary_image"
    val CHOICE_QUIZ_IMAGE_PRIMARY = "choice_quiz_image_primary"
}


data class QuizState(
    val quizId: String,
    val quizType: QuizType,
    val title: String,
    val description: String,
)

enum class QuizType {
    FLASHCARDS,
    CHOICE_QUIZ_PRIMARY_SECONDARY,
    CHOICE_QUIZ_IMAGE_PRIMARY,
}

