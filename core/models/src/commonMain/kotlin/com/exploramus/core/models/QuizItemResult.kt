package com.exploramus.core.models

data class QuizItemResult(
    val id: String,
    val score: Int,
    val errors: Int,
    val totalCorrectCompleted: Int,
    val lastCompletedAt: Long?,
    val lastCorrectAt: Long?,
    val lastErrorAt: Long?
) {
    companion object {
        fun empty(id: String) = QuizItemResult(
            id = id,
            score = 0,
            errors = 0,
            totalCorrectCompleted = 0,
            lastCompletedAt = null,
            lastCorrectAt = null,
            lastErrorAt = null
        )
    }
}

fun QuizItemResult.update(isCorrect: Boolean, timestamp: Long): QuizItemResult {
    return if (isCorrect) onCorrect(timestamp) else onIncorrect(timestamp)
}

fun QuizItemResult.onCorrect(timestamp: Long): QuizItemResult {
    val nextScore = (score + 1).coerceAtMost(MAX_SCORE)
    return copy(
        score = nextScore,
        errors = if (nextScore == MAX_SCORE) 0 else errors,
        totalCorrectCompleted = totalCorrectCompleted + 1,
        lastCompletedAt = timestamp,
        lastCorrectAt = timestamp
    )
}

fun QuizItemResult.onIncorrect(timestamp: Long): QuizItemResult {
    val nextErrors = errors + 1
    return copy(
        score = if (nextErrors >= MAX_ERRORS) 0 else (score - 1).coerceAtLeast(0),
        errors = nextErrors,
        lastCompletedAt = timestamp,
        lastErrorAt = timestamp
    )
}

private const val MAX_SCORE = 4
private const val MAX_ERRORS = 3
