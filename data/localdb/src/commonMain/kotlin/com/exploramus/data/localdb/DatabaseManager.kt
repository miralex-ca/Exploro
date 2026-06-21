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

            val all = listOf(
                COUNTRIES,
                FAVORITES,
                COUNTRY_DETAILS,
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
                emptyList()
            }

            return UserDataSnapshot(
                favorites = favorites
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
            }
        }

        private data class UserDataSnapshot(
            val favorites: List<FavoriteSnapshot>
        )

        data class FavoriteSnapshot(
            val countryId: String,
            val addedAt: Long
        )
    }
}




