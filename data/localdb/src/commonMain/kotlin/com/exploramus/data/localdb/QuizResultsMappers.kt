package com.exploramus.data.localdb

import appLocalDb.QuizItemResults
import appLocalDb.QuizResults
import com.exploramus.core.models.QuizItemResult
import com.exploramus.core.models.QuizResult

internal fun QuizResults.toQuizResult(): QuizResult {
    return QuizResult(
        quizId = quiz_id,
        completedAt = completed_at,
        correctAnswers = correct_answers.toInt(),
        totalAnswers = total_answers.toInt()
    )
}

internal fun List<QuizResults>.toQuizResultList(): List<QuizResult> {
    return this.map { it.toQuizResult() }
}

internal fun QuizItemResults.toQuizItemResult(): QuizItemResult {
    return QuizItemResult(
        id = id,
        score = score.toInt(),
        errors = errors.toInt(),
        totalCorrectCompleted = total_correct_completed.toInt(),
        lastCompletedAt = last_completed_at,
        lastCorrectAt = last_correct_at,
        lastErrorAt = last_error_at
    )
}

internal fun List<QuizItemResults>.toQuizItemResultList(): List<QuizItemResult> {
    return this.map { it.toQuizItemResult() }
}
