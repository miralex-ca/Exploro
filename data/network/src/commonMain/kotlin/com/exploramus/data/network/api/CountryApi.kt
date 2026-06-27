package com.exploramus.data.network.api

import com.exploramus.data.network.ApiClient
import com.exploramus.data.network.NetworkError
import com.exploramus.data.network.NetworkResult
import com.exploramus.data.network.dto.CountriesResponseDto
import com.exploramus.data.network.dto.CountryRawDto
import com.exploramus.data.network.environment.EnvironmentProvider
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json

interface CountryApi {
    suspend fun fetchExample(): NetworkResult<List<CountryRawDto>>
    suspend fun fetchAllRaw(): NetworkResult<List<CountryRawDto>>
}

class CountryApiImpl(
    private val client: ApiClient,
    private val environments: EnvironmentProvider
) : CountryApi  {

    companion object {
        private val ALL_FIELDS = listOf("id", "languages").joinToString(",")
    }

    private fun baseUrl() = environments.current().countriesBaseUrl

    private fun authHeaders() = mapOf(
        HttpHeaders.Authorization to "Bearer ${environments.current().apiKey}"
    )

    private fun allDataUrl() =
        "${baseUrl()}?fields=$ALL_FIELDS"

    override suspend fun fetchExample(): NetworkResult<List<CountryRawDto>> {
        return when (val result = ExampleResponseProvider.get()) {
            is NetworkResult.Success -> NetworkResult.Success(result.data)
            is NetworkResult.Error -> result
        }
    }

    override suspend fun fetchAllRaw(): NetworkResult<List<CountryRawDto>> {
        val result = client.get<CountriesResponseDto>(
            url = allDataUrl(),
            headers = authHeaders()
        )
        return when (result) {
            is NetworkResult.Success -> NetworkResult.Success(result.data.data.objects)
            is NetworkResult.Error -> result
        }
    }
}

object ExampleResponseProvider {
    private val json = Json { ignoreUnknownKeys = true }

    private val responseBody = """
        {
            "data": {
                "objects": [
                    {
                        "id": "FRA",
                        "languages": ["French"]
                    },
                    {
                        "id": "DEU",
                        "languages": ["German"]
                    }
                ],
                "meta": {
                    "total": 2
                }
            }
        }
    """.trimIndent()

    fun get(): NetworkResult<List<CountryRawDto>> = runCatching {
        val parsed = json.decodeFromString<CountriesResponseDto>(responseBody)
        NetworkResult.Success(parsed.data.objects)
    }.getOrElse { NetworkResult.Error(NetworkError.Unknown) }
}