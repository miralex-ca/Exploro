package com.exploramus.data.repository.functions

import com.exploramus.core.models.QuizResult
import com.exploramus.data.repository.Repository

suspend fun Repository.saveQuizResult(
    quizId: String,
    correctAnswers: Int,
    totalAnswers: Int,
    completedAt: Long
) = withRepoContext {
    localDb.saveQuizResult(
        quizId = quizId,
        correctAnswers = correctAnswers,
        totalAnswers = totalAnswers,
        completedAt = completedAt
    )
}

suspend fun Repository.getQuizResults(quizIds: List<String>): List<QuizResult> = withRepoContext {
    localDb.getQuizResults(quizIds)
}

suspend fun Repository.getQuizResult(quizId: String): QuizResult? = withRepoContext {
    localDb.getQuizResult(quizId)
}
