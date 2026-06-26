package viewmodeltests

import com.exploramus.data.common.LocalDataSource
import com.exploramus.shared.TestFakes
import com.exploramus.shared.viewmodel.core.DKMPViewModel
import com.exploramus.shared.viewmodel.core.ScreenIdentifier
import com.exploramus.shared.viewmodel.screens.Screen
import com.exploramus.shared.viewmodel.screens.section.SectionParams
import com.exploramus.shared.viewmodel.screens.section.SectionScreenState
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
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SectionScreenTests {

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
    fun `initSectionScreen sets isLoading false after load`() = runTest {
        val localDb = TestFakes.FakeLocalDataSource().apply {
            addFakeSections("Europe", "Asia")
        }
        val vm = buildViewModel(localDb)
        val id = vm.navigateToSection("Europe")

        assertFalse(vm.sectionState(id).isLoading)
    }

    @Test
    fun `initSectionScreen loads countries for the given continent`() = runTest {
        val localDb = TestFakes.FakeLocalDataSource().apply {
            countriesBySection["Europe"] = listOf(
                TestFakes.country("FRA"),
                TestFakes.country("GER"),
            )
        }
        val vm = buildViewModel(localDb)
        val id = vm.navigateToSection("Europe")

        val countries = vm.sectionState(id).countries
        assertEquals(2, countries.size)
        assertTrue(countries.any { it.id == "FRA" })
        assertTrue(countries.any { it.id == "GER" })
    }


    @Test
    fun `initSectionScreen with empty section loads no countries`() = runTest {
        val vm = buildViewModel()
        val id = vm.navigateToSection("Europe")

        assertTrue(vm.sectionState(id).countries.isEmpty())
        assertFalse(vm.sectionState(id).isLoading)
    }

    private fun buildViewModel(localDb: LocalDataSource = TestFakes.FakeLocalDataSource()) =
        DKMPViewModel(TestFakes.createRepository(localDb = localDb))

    private fun DKMPViewModel.sectionState(screenIdentifier: ScreenIdentifier) =
        navigation.stateProvider
            .getScreenStateFlow(screenIdentifier).value as SectionScreenState

    private fun DKMPViewModel.navigateToSection(continent: String): ScreenIdentifier {
        val screenIdentifier = ScreenIdentifier.get(
            Screen.SectionScreen,
            SectionParams(continent = continent)
        )
        navigation.addScreenToBackstack(screenIdentifier)
        return screenIdentifier
    }
}