package com.exploramus.data.repository

import com.exploramus.core.common.result.DataResult
import com.exploramus.data.common.AssetsDataSource
import com.exploramus.data.common.LocalDataSource
import com.exploramus.data.common.RemoteDataSource
import com.exploramus.data.repository.functions.updateCountriesListData
import com.exploramus.data.repository.utils.TestFakes
import com.russhwolf.settings.MapSettings
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlin.test.Test
import kotlin.test.assertTrue

class UpdateCountriesListDataTest {

    private fun createRepository(
        localDb: LocalDataSource = TestFakes.FakeLocalDataSource(),
        webservices: RemoteDataSource = TestFakes.FakeRemoteDataSource(),
        assets: AssetsDataSource = TestFakes.FakeAssetsDataSource(),
        apiSyncTimestamp: Long = 0L
    ): Repository {
        val settings = MapSettings()
        val repo = Repository(
            localDb = localDb,
            webservices = webservices,
            assetsDataSource = assets,
            settings = settings,
            platformInfo = TestFakes.FakePlatformInfoProvider(),
            dispatchers = TestFakes.TestDispatchersProvider()
        )
        if (apiSyncTimestamp > 0L) {
            repo.localSettings.apiSyncTimestamp = apiSyncTimestamp
        }
        return repo
    }

    @Test
    fun `returns success when data already up to date`() = runTest {
        val repo = createRepository(
            localDb = TestFakes.FakeLocalDataSource(hasData = true),
            apiSyncTimestamp = Clock.System.now().epochSeconds
        )
        val result = repo.updateCountriesListData()
        assertTrue(result is DataResult.Success)
    }

    @Test
    fun `seeds from assets when db is empty and api fails`() = runTest {
        val localDb = TestFakes.FakeLocalDataSource(hasData = false)
        val repo = createRepository(
            localDb = localDb,
            webservices = TestFakes.FakeRemoteDataSource(TestFakes.errorApiResult)
        )
        val result = repo.updateCountriesListData()
        assertTrue(result is DataResult.Success)
        assertTrue(localDb.storedCountries.isNotEmpty())
    }

    @Test
    fun `stores data from api on success`() = runTest {
        val localDb = TestFakes.FakeLocalDataSource(hasData = false)
        val repo = createRepository(
            localDb = localDb,
            webservices = TestFakes.FakeRemoteDataSource(TestFakes.successApiResult)
        )
        repo.updateCountriesListData()
        assertTrue(localDb.storedCountries.isNotEmpty())
        assertTrue(repo.localSettings.apiSyncTimestamp > 0L)
    }

    @Test
    fun `returns success when api fails but db has data`() = runTest {
        val repo = createRepository(
            localDb = TestFakes.FakeLocalDataSource(hasData = true),
            webservices = TestFakes.FakeRemoteDataSource(TestFakes.errorApiResult),
            apiSyncTimestamp = 0L
        )
        val result = repo.updateCountriesListData()
        assertTrue(result is DataResult.Success)
    }
}