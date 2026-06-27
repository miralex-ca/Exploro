package com.exploramus.data.repository.functions

import com.exploramus.core.common.result.DataResult
import com.exploramus.data.common.AssetsDataSource
import com.exploramus.data.repository.utils.TestFakes
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UpdateCountriesListDataTest {
    @Test
    fun `returns success when data is up to date`() = runTest {
        val repo = TestFakes.createRepository(
            localDb = TestFakes.FakeLocalDataSource(hasData = true),
            apiSyncTimestamp = Clock.System.now().epochSeconds
        )
        assertTrue(repo.updateCountriesListData() is DataResult.Success)
    }

    @Test
    fun `stores assets and merges api languages on success`() = runTest {
        val localDb = TestFakes.FakeLocalDataSource()
        val repo = TestFakes.createRepository(localDb = localDb)
        repo.updateCountriesListData()
        assertTrue(localDb.storedCountries.isNotEmpty())
        assertTrue(localDb.storedDetails.isNotEmpty())
        assertTrue(repo.localSettings.apiSyncTimestamp > 0L)
    }

    @Test
    fun `stores assets as-is when api fails`() = runTest {
        val localDb = TestFakes.FakeLocalDataSource()
        val repo = TestFakes.createRepository(
            localDb = localDb,
            webservices = TestFakes.FakeRemoteDataSource(TestFakes.errorApiResult)
        )
        val result = repo.updateCountriesListData()
        assertTrue(result is DataResult.Success)
        assertTrue(localDb.storedCountries.isNotEmpty())
        assertEquals(0L, repo.localSettings.apiSyncTimestamp)
    }

    @Test
    fun `seeds from assets when db is empty`() = runTest {
        val localDb = TestFakes.FakeLocalDataSource(hasData = false)
        val repo = TestFakes.createRepository(
            localDb = localDb,
        )
        val result = repo.updateCountriesListData()
        assertTrue(result is DataResult.Success)
        assertTrue(localDb.storedCountries.isNotEmpty())
    }

    @Test
    fun `returns error when assets fail`() = runTest {
        val repo = TestFakes.createRepository(
            assets = object : AssetsDataSource {
                override suspend fun readAllCountries() = DataResult.Error(null)
                override suspend fun readAllCountryDetails() = DataResult.Error(null)
            }
        )
        assertTrue(repo.updateCountriesListData() is DataResult.Error)
    }

    @Test
    fun `returns success when api fails but db has data`() = runTest {
        val repo = TestFakes.createRepository(
            localDb = TestFakes.FakeLocalDataSource(hasData = true),
            webservices = TestFakes.FakeRemoteDataSource(TestFakes.errorApiResult),
            apiSyncTimestamp = 0L
        )
        val result = repo.updateCountriesListData()
        assertTrue(result is DataResult.Success)
    }
}