package com.exploramus.data.localdb

import app.cash.sqldelight.db.SqlDriver
import appLocalDb.AppLocalDb
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

interface DatabaseManager {
    suspend fun rebuildDatabase()

    class Base(
        private val sqlDriver: SqlDriver,
        private val db: AppLocalDb
    ) : DatabaseManager {

        private object Tables {
            const val COUNTRIES = "Countries"
            const val FAVORITES = "Favorites"
            const val COUNTRY_DETAILS = "CountryDetails"
            const val SECTIONS = "Sections"
            const val QUIZ_RESULTS = "QuizResults"
            const val QUIZ_ITEM_RESULTS = "QuizItemResults"

            val all = listOf(
                COUNTRIES,
                FAVORITES,
                COUNTRY_DETAILS,
                SECTIONS,
                QUIZ_RESULTS,
                QUIZ_ITEM_RESULTS
            )
        }

        private val mutex = Mutex()

        override suspend fun rebuildDatabase() = mutex.withLock {
            val snapshot = captureUserData()
            resetSchema()
            restoreUserData(snapshot)
        }

        private fun resetSchema() {
            dropAllTables()
            AppLocalDb.Schema.create(sqlDriver)
        }

        private fun dropAllTables() {
            Tables.all.forEach { table ->
                sqlDriver.execute(
                    identifier = null,
                    sql = "DROP TABLE IF EXISTS $table",
                    parameters = 0
                )
            }
        }

        private fun captureUserData(): UserDataSnapshot {
            val favorites = try {
                db.favoritesQueries
                    .getFavoritesWithTimestamp()
                    .executeAsList()
                    .map {
                        FavoriteSnapshot(
                            countryId = it.country_id,
                            addedAt = it.added_at
                        )
                    }
            } catch (_: Exception) {
                emptyList<FavoriteSnapshot>()
            }

            val quizResults = try {
                db.quizResultsQueries
                    .getAllQuizResults()
                    .executeAsList()
                    .map {
                        QuizResultSnapshot(
                            quizId = it.quiz_id,
                            completedAt = it.completed_at,
                            correctAnswers = it.correct_answers.toInt(),
                            totalAnswers = it.total_answers.toInt()
                        )
                    }
            } catch (_: Exception) {
                emptyList<QuizResultSnapshot>()
            }

            val quizItemResults = try {
                db.quizItemResultsQueries
                    .getAllQuizItemResults()
                    .executeAsList()
                    .map {
                        QuizItemResultSnapshot(
                            id = it.id,
                            score = it.score.toInt(),
                            errors = it.errors.toInt(),
                            totalCorrectCompleted = it.total_correct_completed.toInt(),
                            lastCompletedAt = it.last_completed_at,
                            lastCorrectAt = it.last_correct_at,
                            lastErrorAt = it.last_error_at
                        )
                    }
            } catch (_: Exception) {
                emptyList<QuizItemResultSnapshot>()
            }

            return UserDataSnapshot(
                favorites = favorites,
                quizResults = quizResults,
                quizItemResults = quizItemResults
            )
        }

        private fun restoreUserData(snapshot: UserDataSnapshot) {
            db.transaction {
                snapshot.favorites.forEach { favorite ->
                    db.favoritesQueries.addFavoriteWithTimestamp(
                        country_id = favorite.countryId,
                        added_at = favorite.addedAt
                    )
                }

                snapshot.quizResults.forEach { result ->
                    db.quizResultsQueries.insertQuizResult(
                        quiz_id = result.quizId,
                        completed_at = result.completedAt,
                        correct_answers = result.correctAnswers.toLong(),
                        total_answers = result.totalAnswers.toLong()
                    )
                }

                snapshot.quizItemResults.forEach { result ->
                    db.quizItemResultsQueries.insertQuizItemResult(
                        id = result.id,
                        score = result.score.toLong(),
                        errors = result.errors.toLong(),
                        total_correct_completed = result.totalCorrectCompleted.toLong(),
                        last_completed_at = result.lastCompletedAt,
                        last_correct_at = result.lastCorrectAt,
                        last_error_at = result.lastErrorAt
                    )
                }
            }
        }

        private data class UserDataSnapshot(
            val favorites: List<FavoriteSnapshot>,
            val quizResults: List<QuizResultSnapshot>,
            val quizItemResults: List<QuizItemResultSnapshot>
        )

        data class FavoriteSnapshot(
            val countryId: String,
            val addedAt: Long
        )

        data class QuizResultSnapshot(
            val quizId: String,
            val completedAt: Long,
            val correctAnswers: Int,
            val totalAnswers: Int
        )

        data class QuizItemResultSnapshot(
            val id: String,
            val score: Int,
            val errors: Int,
            val totalCorrectCompleted: Int,
            val lastCompletedAt: Long?,
            val lastCorrectAt: Long?,
            val lastErrorAt: Long?
        )
    }
}




