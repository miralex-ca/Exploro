package com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist

import com.exploramus.core.models.ChoiceQuizConfig
import com.exploramus.core.models.ChoiceQuizStudyTarget
import com.exploramus.core.models.FlashcardConfig
import com.exploramus.core.models.QuizResult
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
    val sectionId: String = "",
    val unknownCount: Int = 0,
    val familiarCount: Int = 0,
    val masteredCount: Int = 0,
    val eligibleCount: Int = 0,
) {
    @Deprecated("Use sectionId instead", ReplaceWith("sectionId"))
    val continentId: String? get() = sectionId.takeIf { sectionType == QuizzesSectionType.CONTINENT }
}

enum class QuizzesSectionType {
    FAVORITES,
    ALL_COUNTRIES,
    CONTINENT,
}

data class QuizState(
    val quizId: String,
    val quizType: QuizType,
    val result: QuizResult? = null,
)

enum class QuizType {
    FLASHCARDS,
    CHOICE_QUIZ_PRIMARY_SECONDARY,
    CHOICE_QUIZ_IMAGE_PRIMARY,
}

