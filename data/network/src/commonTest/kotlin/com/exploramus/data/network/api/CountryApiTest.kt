package com.exploramus.data.network.api

import com.exploramus.data.network.ApiClient
import com.exploramus.data.network.NetworkResult
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
    fun `fetchAllRaw returns success with valid response`() = runTest {
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
    fun `fetchAllRaw returns correct number of items`() = runTest {
        val api = makeApi {
            respond(
                content = validPageResponse,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val result = api.fetchAllRaw()
        assertEquals(1, (result as NetworkResult.Success).data.size)
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
    fun `fetchAllRaw returns correctly mapped data`() = runTest {
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
        assertEquals("FRA", first.id)
        assertEquals(listOf("French"), first.languages)
    }

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