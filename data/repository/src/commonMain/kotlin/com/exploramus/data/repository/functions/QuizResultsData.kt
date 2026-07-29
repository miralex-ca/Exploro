package com.exploramus.data.repository.functions

import com.exploramus.core.models.QuizItemResult
import com.exploramus.core.models.QuizItemStatus
import com.exploramus.core.models.QuizResult
import com.exploramus.core.models.SectionStats
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

suspend fun Repository.getSectionStats(countryIds: List<String>): SectionStats = withRepoContext {
    val results = localDb.getQuizItemResults(countryIds)
    val resultsMap = results.associateBy { it.id }

    var unknown = 0
    var familiar = 0
    var mastered = 0

    countryIds.forEach { id ->
        val status = resultsMap[id]?.status ?: QuizItemStatus.UNKNOWN
        when (status) {
            QuizItemStatus.UNKNOWN -> unknown++
            QuizItemStatus.FAMILIAR -> familiar++
            QuizItemStatus.MASTERED -> mastered++
        }
    }

    SectionStats(unknown, familiar, mastered)
}
