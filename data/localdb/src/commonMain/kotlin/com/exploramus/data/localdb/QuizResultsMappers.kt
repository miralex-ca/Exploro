package com.exploramus.data.localdb

import appLocalDb.QuizResults
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
