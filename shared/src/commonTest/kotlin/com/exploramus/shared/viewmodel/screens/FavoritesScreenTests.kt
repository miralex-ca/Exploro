package com.exploramus.shared.viewmodel.screens

import com.exploramus.shared.TestFakes
import com.exploramus.shared.viewmodel.core.DKMPViewModel
import com.exploramus.shared.viewmodel.core.ScreenIdentifier
import com.exploramus.shared.viewmodel.screens.favorites.FavoritesScreenState
import com.exploramus.shared.viewmodel.screens.favorites.removeFromFavorites
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesScreenTests {

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
    fun `initFavoritesScreen sets isLoading false after load`() = runTest {
        val vm = buildViewModel()
        val id = vm.navigateToFavorites()
        assertFalse(vm.favoritesState(id).isLoading)
    }

    @Test
    fun `initFavoritesScreen loads empty list when no favorites`() = runTest {
        val vm = buildViewModel()
        val id = vm.navigateToFavorites()
        assertTrue(vm.favoritesState(id).favorites.isEmpty())
    }

    @Test
    fun `initFavoritesScreen loads existing favorites`() = runTest {
        val localDb = TestFakes.FakeLocalDataSource().apply {
            favoritesList.addAll(listOf(
                TestFakes.country("FRA"),
                TestFakes.country("GER"),
            ))
        }
        val vm = buildViewModel(localDb)
        val id = vm.navigateToFavorites()

        val favorites = vm.favoritesState(id).favorites
        assertEquals(2, favorites.size)
        assertTrue(favorites.any { it.id == "FRA" })
        assertTrue(favorites.any { it.id == "GER" })
    }

    @Test
    fun `initFavoritesScreen reloads favorites on each navigation`() = runTest {
        val localDb = TestFakes.FakeLocalDataSource()
        val vm = buildViewModel(localDb)

        val id = vm.navigateToFavorites()
        assertTrue(vm.favoritesState(id).favorites.isEmpty())


        val screenIdentifier = ScreenIdentifier.get(Screen.SearchScreen, null)
        vm.navigation.addScreenToBackstack(screenIdentifier)
        localDb.favoritesList.add(TestFakes.country("FRA"))

        vm.navigateToFavorites()
        assertEquals(1, vm.favoritesState(id).favorites.size)
    }



    @Test
    fun `removeFromFavorites removes country from list`() = runTest {
        val localDb = TestFakes.FakeLocalDataSource().apply {
            favoritesList.addAll(listOf(
                TestFakes.country("FRA"),
                TestFakes.country("GER"),
            ))
        }
        val vm = buildViewModel(localDb)
        val id = vm.navigateToFavorites()

        vm.navigation.events.removeFromFavorites("FRA")

        val favorites = vm.favoritesState(id).favorites
        assertEquals(1, favorites.size)
        assertFalse(favorites.any { it.id == "FRA" })
    }

    @Test
    fun `removeFromFavorites on last item results in empty list`() = runTest {
        val localDb = TestFakes.FakeLocalDataSource().apply {
            favoritesList.add(TestFakes.country("FRA"))
        }
        val vm = buildViewModel(localDb)
        val id = vm.navigateToFavorites()

        vm.navigation.events.removeFromFavorites("FRA")

        assertTrue(vm.favoritesState(id).favorites.isEmpty())
    }

    private fun buildViewModel(localDb: TestFakes.FakeLocalDataSource = TestFakes.FakeLocalDataSource()) =
        DKMPViewModel(TestFakes.createRepository(localDb = localDb))

    private fun DKMPViewModel.favoritesState(screenIdentifier: ScreenIdentifier) =
        navigation.stateProvider
            .getScreenStateFlow(screenIdentifier).value as FavoritesScreenState

    private fun DKMPViewModel.navigateToFavorites(): ScreenIdentifier {
        val screenIdentifier = ScreenIdentifier.get(Screen.FavoritesScreen, null)
        navigation.addScreenToBackstack(screenIdentifier)
        return screenIdentifier
    }

}