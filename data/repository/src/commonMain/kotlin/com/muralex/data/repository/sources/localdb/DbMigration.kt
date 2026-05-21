package com.muralex.data.repository.sources.localdb

import com.muralex.core.common.result.DataError
import com.muralex.core.common.result.DataResult
import com.muralex.data.repository.Repository
import com.muralex.data.repository.utils.repoDebugLogger

suspend fun Repository.migrateDbIfNeeded(): DataResult<Unit> = withRepoContext {
    val currentVersion = localSettings.dbVersion
    val dbVersion = localDb.databaseVersion

    if (currentVersion == dbVersion && !localSettings.shouldForceDbUpdate) {
        repoDebugLogger.log("DB version $currentVersion is up to date.")
        return@withRepoContext DataResult.Success(Unit)
    }

    repoDebugLogger.log("Starting DB migration from version $currentVersion to $dbVersion...")

    return@withRepoContext runCatching {
        localDb.resetAndMigrate()
        localSettings.dbVersion = dbVersion
        localSettings.shouldForceDbUpdate = false
        localSettings.listCacheTimestamp = 0
        repoDebugLogger.log("DB migration: Success")
    }.fold(
        onSuccess = {
            DataResult.Success(Unit)
        },
        onFailure = { error ->
            repoDebugLogger.log("DB migration failed: ${error.message}")
            DataResult.Error(DataError.Database)
        }
    )
}