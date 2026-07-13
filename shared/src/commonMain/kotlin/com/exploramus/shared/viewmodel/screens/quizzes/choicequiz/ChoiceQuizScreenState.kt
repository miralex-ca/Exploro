package com.exploramus.shared.viewmodel.screens.quizzes.choicequiz

import com.exploramus.shared.viewmodel.core.ScreenState

data class ChoiceQuizScreenState(
    val isLoading: Boolean = false,
    val screenTitle: String = "",
    val quiz: ChoiceQuizState = ChoiceQuizState(),
    val isFinished: Boolean = false,
    val totalRestartEvent: Int = 0,
) : ScreenState

data class ChoiceQuizState(
    val items: List<ChoiceQuizItemState> = emptyList(),
    val currentIndex: Int = 0,
    val config: ChoiceQuizConfig = ChoiceQuizConfig(),
) {
    val results: ChoiceQuizResultsState
        get() {
            val answered = items.filter { it.status != ChoiceQuizAnswerStatus.NOT_ANSWERED }
            return ChoiceQuizResultsState(
                correctCount = answered.count { it.status == ChoiceQuizAnswerStatus.CORRECT },
                incorrectCount = answered.count { it.status == ChoiceQuizAnswerStatus.INCORRECT },
                progress = if (items.isEmpty()) 0f else answered.size / items.size.toFloat(),
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
    val prompt: String, // e.g., "What is the capital of this country?"
    val content: String, // e.g., "France" or a URL/resource path
    val contentType: ChoiceQuizContentType = ChoiceQuizContentType.TEXT,
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
    val progress: Float = 0f, // current progress percentage
)

data class ChoiceQuizConfig(
    val showInstantFeedback: Boolean = true,
    val navigationMode: ChoiceQuizNavigationMode = ChoiceQuizNavigationMode.MANUAL,
    val autoSubmitDelayMs: Long = 300L,
    val autoProceedDelayMs: Long = 1000L,
)

enum class ChoiceQuizNavigationMode {
    MANUAL, AUTO
}
