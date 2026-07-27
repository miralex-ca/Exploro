package com.exploramus.data.repository.functions

import com.exploramus.core.models.QuizItemResult
import com.exploramus.core.models.QuizResult
import com.exploramus.data.repository.Repository

suspend fun Repository.saveQuizResult(result: QuizResult) = withRepoContext {
    localDb.saveQuizResult(result)
}

suspend fun Repository.saveQuizResult(
    quizId: String,
    correctAnswers: Int,
    totalAnswers: Int,
    completedAt: Long
) = saveQuizResult(
    QuizResult(
        quizId = quizId,
        correctAnswers = correctAnswers,
        totalAnswers = totalAnswers,
        completedAt = completedAt
    )
)

suspend fun Repository.saveQuizItemResult(result: QuizItemResult) = withRepoContext {
    localDb.saveQuizItemResult(result)
}

suspend fun Repository.getQuizItemResult(id: String): QuizItemResult? = withRepoContext {
    localDb.getQuizItemResult(id)
}

suspend fun Repository.getQuizItemResults(ids: List<String>): List<QuizItemResult> = withRepoContext {
    localDb.getQuizItemResults(ids)
}

suspend fun Repository.getQuizResults(quizIds: List<String>): List<QuizResult> = withRepoContext {
    localDb.getQuizResults(quizIds)
}

suspend fun Repository.getQuizResult(quizId: String): QuizResult? = withRepoContext {
    localDb.getQuizResult(quizId)
}
