package com.exploramus.data.repository.functions

import com.exploramus.core.common.result.DataError
import com.exploramus.core.common.result.DataResult
import com.exploramus.data.repository.utils.TestFakes
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DbMigrationTest {
    @Test
    fun `migrateDbIfNeeded returns success when db is up to date`() = runTest {
        val localDb = TestFakes.FakeLocalDataSource(dbVersion = 1)

        val repository = TestFakes.createRepository(
            localDb = localDb,
        )
        repository.localSettings.apply {
            dbVersion = 1
            shouldForceDbUpdate = false
        }

        val result = repository.migrateDbIfNeeded()

        assertTrue(result is DataResult.Success)
        assertFalse(localDb.migrationCalled)
    }

    @Test
    fun `migrateDbIfNeeded migrates and updates settings`() = runTest {
        val localDb = TestFakes.FakeLocalDataSource(dbVersion = 2)

        val repository = TestFakes.createRepository(
            localDb = localDb,
        )
        repository.localSettings.apply {
            dbVersion = 1
            shouldForceDbUpdate = false
        }

        val result = repository.migrateDbIfNeeded()

        assertTrue(result is DataResult.Success)
        assertTrue(localDb.migrationCalled)
        assertEquals(2, repository.localSettings.dbVersion)
        assertFalse(repository.localSettings.shouldForceDbUpdate)
        assertEquals(0, repository.localSettings.apiSyncTimestamp)
        assertEquals(0, repository.localSettings.listCacheTimestamp)
    }

    @Test
    fun `migrateDbIfNeeded returns error when migration fails`() = runTest {
        val localDb = FailingLocalDataSource(dbVersion = 2)

        val repository = TestFakes.createRepository(
            localDb = localDb,
        )
        repository.localSettings.apply {
            dbVersion = 1
        }

        val result = repository.migrateDbIfNeeded()

        assertTrue(result is DataResult.Error)
        assertEquals(DataError.Database, result.error)
    }
}

class FailingLocalDataSource(dbVersion: Long = 1) : TestFakes.FakeLocalDataSource(dbVersion = dbVersion) {
    override suspend fun resetAndMigrate() {
        throw RuntimeException("DB crash")
    }
}