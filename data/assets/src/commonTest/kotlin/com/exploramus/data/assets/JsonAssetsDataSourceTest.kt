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
        assertTrue(FALLBACK_FILE.isNotBlank())
    }

    @Test
    fun `fallback file name has json extension`() {
        assertTrue(FALLBACK_FILE.endsWith(".json"))
    }

    companion object {
        private val MINIMAL_VALID_JSON = """
            [
              {
                "id": "TST",
                "name": "Test Country",
                "official_name": "Test",
                "capital": "Test City",
                "continent": "Europe",
                "subregion": "Test",
                "flag_png_url": "",
                "flag_alt": "",
                "population": 1000,
                "area": 100.0,
                "coat_of_arms_png_url": "",
                "currency_code": "TST",
                "currency_name": "Test",
                "currency_symbol": "T",
                "languages": ["Test"],
                "maps_google_url": "",
                "maps_osm_url": "",
                "timezones": ["UTC+00:00"],
                "wikipedia_url": "",
                "capital_lat": 0.0,
                "capital_lng": 0.0
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