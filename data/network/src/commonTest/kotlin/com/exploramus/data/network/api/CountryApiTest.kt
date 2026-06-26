package com.exploramus.data.network.api

import com.exploramus.data.network.NetworkResult
import com.exploramus.data.network.ApiClient
import com.exploramus.data.network.api.CountryApiImpl
import com.exploramus.data.network.environment.EnvironmentProvider
import com.exploramus.data.network.environment.NetworkEnvironments
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandler
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CountryApiTest {
    @Test
    fun `fetchAllRaw returns success with valid responses`() = runTest {
        val api = makeApi {
            respond(
                content = validPageResponse,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val result = api.fetchAllRaw()
        assertTrue(result is NetworkResult.Success)
    }

    @Test
    fun `fetchAllRaw merges all pages`() = runTest {
        val api = makeApi {
            respond(
                content = validPageResponse,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val result = api.fetchAllRaw()
        // 3 pages x 1 country each = 3
        assertEquals(3, (result as NetworkResult.Success).data.size)
    }

    @Test
    fun `fetchAllRaw returns error on server error`() = runTest {
        val api = makeApi {
            respond(
                content = "Internal Server Error",
                status = HttpStatusCode.InternalServerError
            )
        }
        val result = api.fetchAllRaw()
        assertTrue(result is NetworkResult.Error)
    }

    @Test
    fun `fetchAllRaw returns correctly mapped countries on success`() = runTest {
        val api = makeApi {
            respond(
                content = validPageResponse,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val result = api.fetchAllRaw()
        val countries = (result as NetworkResult.Success).data
        val first = countries.first()
        assertEquals("FRA", first.codes.alpha3)
        assertEquals("France", first.names.common)
        assertEquals("Paris", first.capitals.first().name)
    }


    private val validPageResponse = """
        {
            "data": {
                "objects": [
                    {
                        "codes": { "alpha_3": "FRA" },
                        "names": { "common": "France", "official": "French Republic" },
                        "flag": { "emoji": "🇫🇷", "url_png": "", "url_svg": "" },
                        "capitals": [{ "name": "Paris", "coordinates": { "lat": 48.87, "lng": 2.33 } }],
                        "continents": ["Europe"],
                        "subregion": "Western Europe",
                        "area": { "kilometers": 551695.0 },
                        "population": 69081996,
                        "languages": [{ "iso639_3": "fra", "name": "French", "native_name": "Français" }],
                        "currencies": [{ "code": "EUR", "name": "Euro", "symbol": "€" }],
                        "links": { "google_maps": "", "open_street_maps": "", "wikipedia": "" },
                        "timezones": ["UTC+01:00"]
                    }
                ],
                "meta": {
                    "total": 1,
                    "count": 1,
                    "limit": 100,
                    "offset": 0,
                    "more": false
                }
            }
        }
    """.trimIndent()

    private fun makeApi(handler: MockRequestHandler): CountryApi {
        val mockEngine = MockEngine(handler)
        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
            expectSuccess = true
        }
        val apiClient = ApiClient.withEngine(httpClient)
        val environments = EnvironmentProvider()
        environments.setEnvironment(NetworkEnvironments.TEST)
        return CountryApiImpl(apiClient, environments)
    }
}