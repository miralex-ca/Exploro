package com.muralex.localdb

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

            val all = listOf(
                COUNTRIES,
                FAVORITES,
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
            return UserDataSnapshot(
                favoriteCountryIds = db.favoritesQueries
                    .getFavoriteIds()
                    .executeAsList()
            )
        }

        private fun restoreUserData(snapshot: UserDataSnapshot) {
            db.transaction {
                snapshot.favoriteCountryIds.forEach { id ->
                    db.favoritesQueries.addFavorite(id)
                }
            }
        }

        private data class UserDataSnapshot(
            val favoriteCountryIds: List<String>
        )
    }
}




