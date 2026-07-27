package com.exploramus.shared.viewmodel.screens.quizzes.choicequiz

import com.exploramus.core.models.ChoiceQuizNavigationMode
import com.exploramus.core.models.ChoiceQuizStudyTarget
import com.exploramus.core.models.Country
import com.exploramus.core.models.QuizResult
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizType
import kotlin.time.Clock

data class ChoiceQuizState(
    val quizId: String = "",
    val items: List<ChoiceQuizItemState> = emptyList(),
    val currentIndex: Int = 0,
    val config: ChoiceQuizConfigState = ChoiceQuizConfigState(),
) {
    val results: ChoiceQuizResultsState
        get() {
            val answered = items.filter { it.status != ChoiceQuizAnswerStatus.NOT_ANSWERED }
            return ChoiceQuizResultsState(
                correctCount = answered.count { it.status == ChoiceQuizAnswerStatus.CORRECT },
                incorrectCount = answered.count { it.status == ChoiceQuizAnswerStatus.INCORRECT },
                totalCount = items.size,
                skipped = items.size - answered.size,
                progress = if (items.isEmpty()) 0f else answered.size / items.size.toFloat(),
                score = if (items.isEmpty()) 0f else answered.count { it.status == ChoiceQuizAnswerStatus.CORRECT } / items.size.toFloat(),
                items = items
            )
        }
}

data class ChoiceQuizItemState(
    val id: String,
    val question: ChoiceQuizQuestionState,
    val options: List<ChoiceQuizOptionState>,
    val correctOptionId: String,
    val selectedOptionId: String? = null,
    val isSubmitted: Boolean = false,
) {
    val status: ChoiceQuizAnswerStatus
        get() = when {
            !isSubmitted -> ChoiceQuizAnswerStatus.NOT_ANSWERED
            selectedOptionId == correctOptionId -> ChoiceQuizAnswerStatus.CORRECT
            else -> ChoiceQuizAnswerStatus.INCORRECT
        }
}

data class ChoiceQuizQuestionState(
    val content: String, // e.g., "France" or a URL/resource path
    val contentType: ChoiceQuizContentType = ChoiceQuizContentType.TEXT,
    val studyTarget: ChoiceQuizStudyTarget,
)

data class ChoiceQuizOptionState(
    val id: String,
    val content: String,
    val contentType: ChoiceQuizContentType = ChoiceQuizContentType.TEXT,
)

enum class ChoiceQuizContentType {
    TEXT, IMAGE
}

enum class ChoiceQuizAnswerStatus {
    NOT_ANSWERED, CORRECT, INCORRECT
}

data class ChoiceQuizResultsState(
    val correctCount: Int = 0,
    val incorrectCount: Int = 0,
    val totalCount: Int = 0,
    val skipped: Int = 0,
    val progress: Float = 0f, // current progress percentage (answered/total)
    val score: Float = 0f, // score percentage (correct/total)
    val items: List<ChoiceQuizItemState> = emptyList(),
)

data class ChoiceQuizConfigState(
    val showInstantFeedback: Boolean = true,
    val autoSubmitDelayMs: Long = 200L,
    val autoProceedCorrectDelayMs: Long = 700L,
    val autoProceedIncorrectDelayMs: Long = 1000L,
    val navigationMode: ChoiceQuizNavigationMode = ChoiceQuizNavigationMode.MANUAL,
)

data class ChoiceQuizEvaluation(
    val title: String,
    val emoji: String,
    val color: Long
)

fun ChoiceQuizState.selectOption(itemId: String, optionId: String): ChoiceQuizState {
    val updatedItems = items.map { item ->
        if (item.id == itemId && !item.isSubmitted) {
            item.copy(selectedOptionId = optionId)
        } else {
            item
        }
    }
    return copy(items = updatedItems)
}

fun ChoiceQuizState.submitAnswer(itemId: String): Pair<ChoiceQuizState, Boolean> {
    var didSubmit = false
    val updatedItems = items.map { item ->
        if (item.id == itemId && item.selectedOptionId != null && !item.isSubmitted) {
            didSubmit = true
            item.copy(isSubmitted = true)
        } else {
            item
        }
    }
    return copy(items = updatedItems) to didSubmit
}

fun ChoiceQuizState.next(): Pair<ChoiceQuizState, Boolean> {
    val nextIndex = currentIndex + 1
    return if (nextIndex < items.size) {
        copy(currentIndex = nextIndex) to false
    } else {
        this to true // true = finished
    }
}

fun ChoiceQuizState.toQuizResult(): QuizResult {
    val res = results
    return QuizResult(
        quizId = quizId,
        correctAnswers = res.correctCount,
        totalAnswers = res.totalCount,
        completedAt = Clock.System.now().toEpochMilliseconds()
    )
}

fun QuizType.toDefaultStudyTarget(): ChoiceQuizStudyTarget = when(this) {
    QuizType.CHOICE_QUIZ_IMAGE_PRIMARY -> ChoiceQuizStudyTarget.IMAGE_PRIMARY
    else -> ChoiceQuizStudyTarget.PRIMARY_SECONDARY
}

fun buildChoiceQuizItem(
    target: Country,
    distractorPool: List<Country>,
    studyTarget: ChoiceQuizStudyTarget
): ChoiceQuizItemState {
    val distractors = distractorPool
        .filter { it.id != target.id }
        .shuffled()
        .take(3)

    // 2. Build options based on studyTarget
    val options = (distractors + target).shuffled().map { country ->
        val content = when (studyTarget) {
            ChoiceQuizStudyTarget.PRIMARY_SECONDARY -> country.capital
            ChoiceQuizStudyTarget.SECONDARY_PRIMARY -> country.name
            ChoiceQuizStudyTarget.IMAGE_PRIMARY -> country.name
            ChoiceQuizStudyTarget.PRIMARY_IMAGE -> country.flagImage
        }
        val contentType = if (studyTarget == ChoiceQuizStudyTarget.PRIMARY_IMAGE) {
            ChoiceQuizContentType.IMAGE
        } else {
            ChoiceQuizContentType.TEXT
        }
        ChoiceQuizOptionState(id = country.id, content = content, contentType = contentType)
    }

    // 3. Build question based on studyTarget
    val questionContent = when (studyTarget) {
        ChoiceQuizStudyTarget.PRIMARY_SECONDARY -> target.name
        ChoiceQuizStudyTarget.SECONDARY_PRIMARY -> target.capital
        ChoiceQuizStudyTarget.IMAGE_PRIMARY -> target.flagImage
        ChoiceQuizStudyTarget.PRIMARY_IMAGE -> target.name
    }

    val questionContentType = if (studyTarget == ChoiceQuizStudyTarget.IMAGE_PRIMARY) {
        ChoiceQuizContentType.IMAGE
    } else {
        ChoiceQuizContentType.TEXT
    }

    return ChoiceQuizItemState(
        id = target.id,
        question = ChoiceQuizQuestionState(
            content = questionContent,
            contentType = questionContentType,
            studyTarget = studyTarget,
        ),
        options = options,
        correctOptionId = target.id
    )
}
