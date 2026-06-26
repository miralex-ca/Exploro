package com.exploramus.shared.viewmodel.screens

import com.exploramus.data.common.LocalDataSource
import com.exploramus.shared.TestFakes
import com.exploramus.shared.viewmodel.core.DKMPViewModel
import com.exploramus.shared.viewmodel.core.ScreenIdentifier
import com.exploramus.shared.viewmodel.screens.home.HomeScreenState
import com.exploramus.shared.viewmodel.screens.home.retryBootstrapApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenTests {
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
    fun `initHomeScreen first run loads sections and sets isLoading false`() = runTest {
        val localDb = TestFakes.FakeLocalDataSource().apply {
            addFakeSections("Europe", "Asia")
        }

        val vm = buildViewModel(localDb)
        val id = vm.navigateToHome()

        assertTrue(vm.navigation.stateManager.dataRepository.runtimeCache.isBootstrapped)
        assertFalse(vm.homeState(id).isLoading)
        assertTrue(vm.homeState(id).homeSections.isNotEmpty())
    }

    @Test
    fun `initHomeScreen first not loading when migration fails`() = runTest {
        val localDb = FailingLocalDataSource(dbVersion = 2)
        val repo = TestFakes.createRepository(localDb = localDb).apply {
            localSettings.dbVersion = 1
        }
        val vm = DKMPViewModel(repo)
        val id = vm.navigateToHome()

        assertTrue(vm.homeState(id).isLoading)
    }

    @Test
    fun `retry bootstrap loads sections after successful retry`() = runTest {
        val localDb = TestFakes.FakeLocalDataSource()
        val vm = buildViewModel(localDb)
        val id = vm.navigateToHome()

        localDb.addFakeSections("Europe", "Asia")

        vm.navigation.events.retryBootstrapApp()

        assertFalse(vm.homeState(id).isLoading)
        assertTrue(vm.homeState(id).homeSections.isNotEmpty())
    }

    private fun buildViewModel(localDb: LocalDataSource = TestFakes.FakeLocalDataSource()) =
        DKMPViewModel(TestFakes.createRepository(localDb = localDb))

    private fun DKMPViewModel.homeState(screenIdentifier: ScreenIdentifier) =
        navigation.stateProvider
            .getScreenStateFlow(screenIdentifier).value as HomeScreenState

    private fun DKMPViewModel.navigateToHome(): ScreenIdentifier {
        val screenIdentifier = ScreenIdentifier.get(Screen.HomeScreen, null)
        navigation.addScreenToBackstack(screenIdentifier)
        return screenIdentifier
    }
}