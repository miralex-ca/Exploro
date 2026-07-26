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

            val all = listOf(
                COUNTRIES,
                FAVORITES,
                COUNTRY_DETAILS,
                SECTIONS,
                QUIZ_RESULTS
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

            return UserDataSnapshot(
                favorites = favorites,
                quizResults = quizResults
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
            }
        }

        private data class UserDataSnapshot(
            val favorites: List<FavoriteSnapshot>,
            val quizResults: List<QuizResultSnapshot>
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
    }
}




