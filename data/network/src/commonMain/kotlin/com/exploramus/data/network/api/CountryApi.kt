package com.exploramus.data.network.api

import com.exploramus.data.network.ApiClient
import com.exploramus.data.network.NetworkResult
import com.exploramus.data.network.dto.CountriesResponseDto
import com.exploramus.data.network.dto.CountryDetailsDto
import com.exploramus.data.network.dto.CountryDto
import com.exploramus.data.network.dto.CountryRawDto
import com.exploramus.data.network.environment.EnvironmentProvider
import io.ktor.http.HttpHeaders


class CountryApi(
    private val client: ApiClient,
    private val environments: EnvironmentProvider
) {

    companion object {
        private const val PAGE_SIZE = 100
        private val OFFSETS = listOf(0, 100, 200)

        private val ALL_FIELDS = listOf(
            "codes.alpha_3",
            "names.common", "names.official",
            "flag.url_png", "flag.url_svg", "flag.emoji",
            "capitals", "continents", "subregion",
            "area.kilometers", "population",
            "languages", "currencies",
            "links.google_maps", "links.open_street_maps", "links.wikipedia",
            "timezones"
        ).joinToString(",")
    }

    private fun baseUrl() = environments.current().countriesBaseUrl

    private fun authHeaders() = mapOf(
        HttpHeaders.Authorization to "Bearer ${environments.current().apiKey}"
    )

    suspend fun fetchAllRaw(): NetworkResult<List<CountryRawDto>> {
        val pages = OFFSETS.map { fetchPage(it) }
        val error = pages.filterIsInstance<NetworkResult.Error>().firstOrNull()
        if (error != null) return error
        return NetworkResult.Success(
            pages.filterIsInstance<NetworkResult.Success<List<CountryRawDto>>>()
                .flatMap { it.data }
        )
    }

    private fun pageUrl(offset: Int) =
        "${baseUrl()}?limit=$PAGE_SIZE&offset=$offset&response_fields=$ALL_FIELDS"

    private suspend fun fetchPage(offset: Int): NetworkResult<List<CountryRawDto>> {
        val result = client.get<CountriesResponseDto>(
            url = pageUrl(offset),
            headers = authHeaders()
        )
        return when (result) {
            is NetworkResult.Success -> NetworkResult.Success(result.data.data.objects)
            is NetworkResult.Error -> result
        }
    }
}