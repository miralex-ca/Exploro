package com.muralex.network.api

import com.muralex.network.ApiClient
import com.muralex.network.environment.EnvironmentProvider
import com.muralex.network.dto.CountryDto

class CountryApi(
    private val client: ApiClient,
    private val environments: EnvironmentProvider
) {

    companion object {
        private const val BASE_FIELDS = "cca3,name,flags,population,capital,region"

        private const val DETAILS_FIELDS = "cca3,name,flags,population,capital,region,subregion"

        private const val ALL = "/v3.1/all"

        private const val NAME = "/v3.1/name"

        private const val ALPHA = "/v3.1/alpha"
    }

    suspend fun fetchCountries(): List<CountryDto> {
        return client.get(
            url = environments.current().countriesBaseUrl + "$ALL?fields=$BASE_FIELDS"
        )
    }

    suspend fun fetchCountryDetails(code: String): CountryDto? {
        val response: List<CountryDto> =
            client.get(
                url = environments.current().countriesBaseUrl + "$ALPHA/$code?fields=$DETAILS_FIELDS"
            )
        return response.firstOrNull()
    }

    suspend fun searchCountries(
        query: String
    ): List<CountryDto> {
        return client.get(
            url = environments.current().countriesBaseUrl + "$NAME/$query?fields=$BASE_FIELDS"
        )
    }
}