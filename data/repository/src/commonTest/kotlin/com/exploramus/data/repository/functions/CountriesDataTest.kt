package com.exploramus.data.repository.functions

import com.exploramus.core.models.Section
import com.exploramus.data.repository.utils.TestFakes
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CountriesDataTest {
    private fun country(id: String) =
        TestFakes.country.copy(
            id = id,
            name = id
        )

    @Test
    fun `getHomeSections returns all valid sections`() = runTest {
        val localDb = TestFakes.FakeLocalDataSource()
        localDb.storedSections = listOf(
            Section("EU", "Europe"),
            Section("AS", "Asia")
        )

        localDb.countriesBySection["EU"] = listOf(
            country("FRA"),
            country("GER"),
            country("ITA")
        )

        localDb.countriesBySection["AS"] = listOf(
            country("JPN"),
            country("CHN"),
            country("KOR")
        )

        val repository = TestFakes.createRepository(localDb = localDb)
        val result = repository.getHomeSections()
        assertEquals(2, result.size)
        assertEquals("EU", result[0].sectionId)
        assertEquals("AS", result[1].sectionId)

    }

    @Test
    fun `getHomeSections returns only sections with at least 3 countries`() = runTest {

        val localDb = TestFakes.FakeLocalDataSource()
        localDb.storedSections = listOf(
            Section("EU", "Europe"),
            Section("AS", "Asia")
        )

        localDb.countriesBySection["EU"] = listOf(
            country("FRA"),
            country("GER"),
            country("ITA")
        )

        localDb.countriesBySection["AS"] = listOf(
            country("JPN"),
            country("CHN")
        )

        val repository = TestFakes.createRepository(localDb)

        val result = repository.getHomeSections()

        assertEquals(1, result.size)

        val europe = result.first()

        assertEquals("EU", europe.sectionId)
        assertEquals(3, europe.countries.size)
    }

    @Test
    fun `getHomeSections returns empty when no sections have enough countries`() = runTest {
        val localDb = TestFakes.FakeLocalDataSource()
        localDb.storedSections = listOf(
            Section("EU", "Europe")
        )

        localDb.countriesBySection["EU"] = listOf(
            country("FRA"),
            country("GER")
        )

        val repository = TestFakes.createRepository(localDb)
        val result = repository.getHomeSections()
        assertTrue(result.isEmpty())
    }

    @Test
    fun `searchCountries returns search results`() = runTest {
        val localDb = TestFakes.FakeLocalDataSource()

        val france = country("FRA")
        val finland = country("FIN")

        localDb.searchResult = listOf(
            france,
            finland
        )

        val repository = TestFakes.createRepository(
            localDb = localDb
        )

        val result = repository.searchCountries("fr")

        assertEquals(2, result.size)
        assertEquals(france, result[0])
        assertEquals(finland, result[1])
        assertEquals("fr", localDb.lastSearchQuery)
    }
}

