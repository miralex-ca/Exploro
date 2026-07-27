package com.exploramus.core.models

data class QuizItemResult(
    val id: String,
    val score: Int,
    val errors: Int,
    val totalCorrectCompleted: Int,
    val lastCompletedAt: Long?,
    val lastCorrectAt: Long?,
    val lastErrorAt: Long?
)
