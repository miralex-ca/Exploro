package com.exploramus.core.models

data class QuizResult(
    val quizId: String,
    val completedAt: Long,
    val correctAnswers: Int,
    val totalAnswers: Int
) {
    val score: Float
        get() = if (totalAnswers > 0) correctAnswers.toFloat() / totalAnswers else 0f
}
