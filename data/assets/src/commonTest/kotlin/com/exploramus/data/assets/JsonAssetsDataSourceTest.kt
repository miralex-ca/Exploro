package com.exploramus.data.assets

import com.exploramus.core.common.result.DataResult
import com.exploramus.data.common.AssetFileReader
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class JsonAssetsDataSourceTest {

    @Test
    fun `returns success when file is valid json array`() = runTest {
        val source = JsonAssetsDataSource(validJsonReader())
        val result = source.readAllCountries()
        assertTrue(result is DataResult.Success)
    }

    @Test
    fun `returns non empty list when file has entries`() = runTest {
        val source = JsonAssetsDataSource(validJsonReader())
        val result = source.readAllCountries()
        assertTrue((result as DataResult.Success).data.isNotEmpty())
    }

    @Test
    fun `returns error when file is missing`() = runTest {
        val source = JsonAssetsDataSource(missingFileReader())
        val result = source.readAllCountries()
        assertTrue(result is DataResult.Error)
    }

    @Test
    fun `returns error when file is malformed json`() = runTest {
        val source = JsonAssetsDataSource(malformedJsonReader())
        val result = source.readAllCountries()
        assertTrue(result is DataResult.Error)
    }

    @Test
    fun `returns empty list when json array is empty`() = runTest {
        val source = JsonAssetsDataSource(emptyArrayReader())
        val result = source.readAllCountries()
        assertTrue((result as DataResult.Success).data.isEmpty())
    }

    @Test
    fun `details list is not empty when file is valid`() = runTest {
        val source = JsonAssetsDataSource(validJsonReader())
        val result = source.readAllCountryDetails()
        assertTrue((result as DataResult.Success).data.isNotEmpty())
    }

    @Test
    fun `fallback file name is not blank`() {
        assertTrue(COUNTRIES_DATA_FILE.isNotBlank())
    }

    companion object {
        private val MINIMAL_VALID_JSON = """
            [
              {
                "id": "FRA",
        "iso2": "fr",
        "name": "France",
        "official_name": "French Republic",
        "capital": "Paris",
        "continent": "Europe",
        "location": "Western Europe",
        "flag_image": "https://flagcdn.com/w640/fr.png",
        "flag_emoji": "🇫🇷"
              }
            ]
        """.trimIndent()

        private fun validJsonReader() = object : AssetFileReader {
            override fun readFile(fileName: String) = MINIMAL_VALID_JSON
        }

        private fun missingFileReader() = object : AssetFileReader {
            override fun readFile(fileName: String) = error("File not found: $fileName")
        }

        private fun malformedJsonReader() = object : AssetFileReader {
            override fun readFile(fileName: String) = "{ not valid json ["
        }

        private fun emptyArrayReader() = object : AssetFileReader {
            override fun readFile(fileName: String) = "[]"
        }
    }
}