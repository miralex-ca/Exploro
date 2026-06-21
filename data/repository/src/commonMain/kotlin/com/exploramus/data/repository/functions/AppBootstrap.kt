package com.exploramus.data.repository.functions

import com.exploramus.core.common.logging.Log
import com.exploramus.core.common.result.DataError
import com.exploramus.core.common.result.DataResult
import com.exploramus.data.repository.Repository


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
        localSettings.apiSyncTimestamp = 0
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

suspend fun Repository.hasCountriesData(): Boolean = withRepoContext {
    localDb.hasCountriesData()
}