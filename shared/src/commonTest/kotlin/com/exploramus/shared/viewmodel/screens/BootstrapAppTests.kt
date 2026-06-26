package com.exploramus.shared.viewmodel.screens

import com.exploramus.core.common.result.DataResult
import com.exploramus.shared.FailingLocalDataSource
import com.exploramus.shared.TestFakes
import com.exploramus.shared.viewmodel.appstate.AppStartupState
import com.exploramus.shared.viewmodel.core.StateManager
import com.exploramus.shared.viewmodel.screens.home.BootstrapResult
import com.exploramus.shared.viewmodel.screens.home.bootstrapApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BootstrapAppTests {

    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `bootstrap fails when migration fails`() = runTest {
        val localDb = FailingLocalDataSource(dbVersion = 2)
        val repo = TestFakes.createRepository(localDb = localDb)

        repo.localSettings.apply {
            dbVersion = 1
        }

        val stateManager = StateManager(repo)
        val result = stateManager.bootstrapApp()

        assertEquals(BootstrapResult.Failure, result)
        assertEquals(
            AppStartupState.Failure.UnexpectedError,
            stateManager.appStartupState.value
        )
    }

    @Test
    fun `bootstrap succeeds when migration and sync succeeds`() = runTest {
        val localDb =  TestFakes.FakeLocalDataSource(dbVersion = 2)
        val repo = TestFakes.createRepository(localDb = localDb)

        repo.localSettings.apply {
            dbVersion = 1
        }

        val stateManager = StateManager(repo)
        val result = stateManager.bootstrapApp()

        assertEquals(BootstrapResult.Success, result)
        assertEquals(
            AppStartupState.Ready,
            stateManager.appStartupState.value
        )
    }

    @Test
    fun `bootstrap succeeds when migration succeeds sync fails`() = runTest {
        val localDb =  TestFakes.FakeLocalDataSource(dbVersion = 2)

        val remoteDataSource = TestFakes.FakeRemoteDataSource(
            result = DataResult.Error(null)
        )
        val repo = TestFakes.createRepository(localDb = localDb, webservices = remoteDataSource)

        repo.localSettings.apply {
            dbVersion = 1
        }

        val stateManager = StateManager(repo)
        val result = stateManager.bootstrapApp()

        assertEquals(BootstrapResult.Success, result)
        assertEquals(
            AppStartupState.Ready,
            stateManager.appStartupState.value
        )
    }
 }