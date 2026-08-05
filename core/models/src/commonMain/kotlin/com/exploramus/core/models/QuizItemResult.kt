package com.exploramus.core.models

enum class QuizItemStatus {
    UNKNOWN, FAMILIAR, MASTERED
}

data class QuizItemResult(
    val id: String,
    val score: Int,
    val errors: Int,
    val totalCorrectCompleted: Int,
    val lastCompletedAt: Long?,
    val lastCorrectAt: Long?,
    val lastErrorAt: Long?
) {
    val status: QuizItemStatus
        get() = when (score) {
            0 -> QuizItemStatus.UNKNOWN
            1, 2 -> QuizItemStatus.FAMILIAR
            3, 4 -> QuizItemStatus.MASTERED
            else -> QuizItemStatus.UNKNOWN
        }

    /**
     * A calculated value used to compare mastery across different items.
     * Lower values indicate items that need more practice (high errors, low score).
     * Higher values indicate stronger mastery.
     */
    val masteryRating: Double
        get() {
            // Weights to ensure the order of importance: Errors > Score > Total Correct > Recency
            // Errors are inverted (more errors = lower rating)
            val errorFactor = -errors.toDouble() * 1000.0
            val scoreFactor = score.toDouble() * 100.0
            val totalCorrectFactor = totalCorrectCompleted.toDouble() * 1.0
            // Recency as a small fractional tie-breaker (normalized to roughly 0.0-1.0 range)
            val recencyFactor = (lastCorrectAt?.toDouble() ?: 0.0) / 1_000_000_000_000.0

            return errorFactor + scoreFactor + totalCorrectFactor + recencyFactor
        }

    companion object {
        val MasteryComparator = compareBy<QuizItemResult> { it.masteryRating }

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
    val nextErrors = if (nextScore == MAX_SCORE) {
        0
    } else {
        (errors - 1).coerceAtLeast(0)
    }
    return copy(
        score = nextScore,
        errors = nextErrors,
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
