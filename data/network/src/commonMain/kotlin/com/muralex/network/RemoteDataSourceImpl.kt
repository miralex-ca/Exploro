package com.muralex.network

import com.muralex.data.common.RemoteDataSource

import com.muralex.models.Country
import com.muralex.network.api.CountryApi
import com.muralex.network.dto.entity

class RemoteDataSourceImpl(
    private val countryApi: CountryApi
) : RemoteDataSource {

    override suspend fun fetchCountries(): List<Country> {
        return countryApi
            .fetchCountries()
            .entity()
    }

    override suspend fun fetchCountryDetails(code: String): Country? {
        return countryApi
            .fetchCountryDetails(code)
            ?.entity
    }

    override suspend fun searchCountries(query: String): List<Country> {
        return countryApi
            .searchCountries(query)
            .entity()
    }
}
