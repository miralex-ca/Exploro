package com.muralex.data.repository.sources.localdb

import com.muralex.core.common.logging.Log
import com.muralex.core.common.result.DataError
import com.muralex.core.common.result.DataResult
import com.muralex.data.repository.Repository

suspend fun Repository.migrateDbIfNeeded(): DataResult<Unit> = withRepoContext {
    val currentVersion = localSettings.dbVersion
    val dbVersion = localDb.databaseVersion

    if (currentVersion == dbVersion && !localSettings.shouldForceDbUpdate) {
        Log.d("DB version $currentVersion is up to date.")
        return@withRepoContext DataResult.Success(Unit)
    }

    Log.d("Starting DB migration from version $currentVersion to $dbVersion...")

    return@withRepoContext runCatching {
        localDb.resetAndMigrate()
        localSettings.dbVersion = dbVersion
        localSettings.shouldForceDbUpdate = false
        localSettings.listCacheTimestamp = 0
        Log.d("DB migration: Success")
    }.fold(
        onSuccess = {
            DataResult.Success(Unit)
        },
        onFailure = { error ->
            Log.e("DB migration failed: ${error.message}")
            DataResult.Error(DataError.Database)
        }
    )
}