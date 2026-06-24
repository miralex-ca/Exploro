package com.exploramus.data.network

import com.exploramus.data.network.dto.CountriesResponseDto
import com.exploramus.data.network.dto.CountryRawDto
import com.exploramus.data.network.dto.toCountry
import com.exploramus.data.network.dto.toCountryDetails
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
    fun `json parses correct alpha3 and name`() {
        val dto = parseResponse()
        assertEquals("FRA", dto.codes.alpha3)
        assertEquals("France", dto.names.common)
    }

    @Test
    fun `dto maps to country with correct id`() {
        assertEquals("FRA", parseResponse().toCountry().id)
    }

    @Test
    fun `dto maps to details with correct id`() {
        assertEquals("FRA", parseResponse().toCountryDetails().id)
    }

    @Test
    fun `dto maps to details with correct population`() {
        assertEquals(69081996, parseResponse().toCountryDetails().population)
    }


    private val json = Json { ignoreUnknownKeys = true }

    private val validPageResponse = """
        {
            "data": {
                "objects": [
                    {
                        "codes": { "alpha_3": "FRA" },
                        "names": { "common": "France", "official": "French Republic" },
                        "flag": { "emoji": "🇫🇷", "url_png": "https://flag.png", "url_svg": "" },
                        "capitals": [{ "name": "Paris", "coordinates": { "lat": 48.87, "lng": 2.33 } }],
                        "continents": ["Europe"],
                        "subregion": "Western Europe",
                        "area": { "kilometers": 551695.0 },
                        "population": 69081996,
                        "languages": [{ "iso639_3": "fra", "name": "French", "native_name": "Français" }],
                        "currencies": [{ "code": "EUR", "name": "Euro", "symbol": "€" }],
                        "links": { "google_maps": "", "open_street_maps": "", "wikipedia": "https://en.wikipedia.org/wiki/France" },
                        "timezones": ["UTC+01:00"]
                    }
                ],
                "meta": { "total": 1, "count": 1, "limit": 100, "offset": 0, "more": false }
            }
        }
    """.trimIndent()

    private fun parseResponse(): CountryRawDto {
        val response = json.decodeFromString<CountriesResponseDto>(validPageResponse)
        return response.data.objects.first()
    }

}