package com.muralex.network.api

import com.muralex.network.ApiClient
import com.muralex.network.NetworkResult
import com.muralex.network.dto.CountryDetailsDto
import com.muralex.network.dto.CountryDto
import com.muralex.network.environment.EnvironmentProvider
import io.ktor.http.HttpHeaders

class CountryApi(
    private val client: ApiClient,
    private val environments: EnvironmentProvider
) {

    companion object {
        private const val BASE_FIELDS = "cca3,name,capital,flags,flag,continents,subregion"
        private const val DETAILS_FIELDS =
            "cca3,area,coatOfArms,population,languages,currencies,maps,timezones"

        private const val ALL = "/v3.1/all"

        private const val NAME = "/v3.1/name"

        private const val ALPHA = "/v3.1/alpha"
    }

    private fun authHeaders() = mapOf(
        HttpHeaders.Authorization to "Bearer ${environments.current().apiKey}"
    )

    suspend fun fetchAllCountries(): NetworkResult<List<CountryDto>> {
        return client.get(
            url = environments.current().countriesBaseUrl + "$ALL?fields=$BASE_FIELDS",
            headers = authHeaders()
        )
    }

    suspend fun fetchAllCountryDetails(): NetworkResult<List<CountryDetailsDto>> {
        return client.get(
            url = environments.current().countriesBaseUrl + "$ALL?fields=$DETAILS_FIELDS",
            headers = authHeaders()
        )
    }

    suspend fun fetchCountryDetails(code: String): NetworkResult<CountryDto> {
        return client.get(
            url = environments.current().countriesBaseUrl + "$ALPHA/$code?fields=$DETAILS_FIELDS",
            headers = authHeaders()
        )
    }

    suspend fun searchCountries(query: String): NetworkResult<List<CountryDto>> {
        return client.get(
            url = environments.current().countriesBaseUrl + "$NAME/$query?fields=$BASE_FIELDS",
            headers = authHeaders()
        )
    }
}