package com.exploramus.data.network.dto

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CountryMappingTest {
    @Test
    fun `json parses to response dto without error`() {
        val response = json.decodeFromString<CountriesResponseDto>(validPageResponse)
        assertTrue(response.data.objects.isNotEmpty())
    }

    @Test
    fun `json parses correct id and languages`() {
        val dto = parseResponse()
        assertEquals("FRA", dto.id)
        assertEquals(listOf("French"), dto.languages)
    }

    @Test
    fun `dto maps to country info with correct id and languages`() {
        val info = parseResponse().toCountryInfo()
        assertEquals("FRA", info.id)
        assertEquals(listOf("French"), info.languages)
    }

    private val json = Json { ignoreUnknownKeys = true }

    private val validPageResponse = """
        {
            "data": {
                "objects": [
                    {
                        "id": "FRA",
                        "languages": ["French"]
                    }
                ],
                "meta": {
                    "total": 1
                }
            }
        }
    """.trimIndent()

    private fun parseResponse(): CountryRawDto {
        val response = json.decodeFromString<CountriesResponseDto>(validPageResponse)
        return response.data.objects.first()
    }
}
