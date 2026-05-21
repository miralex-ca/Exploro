package com.muralex.data.repository.sources.localdb

import com.muralex.data.repository.Repository
import com.muralex.data.repository.utils.repoDebugLogger

suspend fun Repository.migrateDbIfNeeded() = withRepoContext {
    val currentVersion = localSettings.dbVersion
    val dbVersion = localDb.databaseVersion

    if (currentVersion == dbVersion && !localSettings.shouldForceDbUpdate) {
        repoDebugLogger.log("DB version $currentVersion is up to date.")
        return@withRepoContext
    }

    repoDebugLogger.log(
        "Starting DB migration from version $currentVersion to $dbVersion..."
    )

    runCatching {
        localDb.resetAndMigrate()
        localSettings.dbVersion = dbVersion
        localSettings.shouldForceDbUpdate = false
        localSettings.listCacheTimestamp = 0
        repoDebugLogger.log("DB migration: Success")
    }.onFailure { error ->
        repoDebugLogger.log(
            "DB migration failed: ${error.message}"
        )

        throw error
    }
}