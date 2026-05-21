package com.muralex.network

import com.muralex.core.common.result.DataResult
import com.muralex.data.common.RemoteDataSource
import com.muralex.models.Country
import com.muralex.models.CountryDetails
import com.muralex.network.api.CountryApi
import com.muralex.network.dto.entity

class RemoteDataSourceImpl(
    private val countryApi: CountryApi
) : RemoteDataSource {

    override suspend fun fetchAllCountries(): DataResult<List<Country>> {
        return when (val result = countryApi.fetchAllCountries()) {
            is NetworkResult.Success -> {
                DataResult.Success(
                    result.data.entity()
                )
            }

            is NetworkResult.Error -> {
                DataResult.Error(result.error.toDataError())
            }
        }
    }

    override suspend fun fetchAllCountryDetails(): DataResult<List<CountryDetails>> {
        return when (val result = countryApi.fetchAllCountryDetails()) {
            is NetworkResult.Success -> {
                DataResult.Success(
                    result.data.entity()
                )
            }
            is NetworkResult.Error -> {
                DataResult.Error(result.error.toDataError())
            }
        }
    }

    override suspend fun fetchCountryDetails(code: String): DataResult<Country> {
        return when (val result = countryApi.fetchCountryDetails(code)) {
            is NetworkResult.Success -> {
                DataResult.Success(result.data.entity)
            }
            is NetworkResult.Error -> {
                DataResult.Error(result.error.toDataError())
            }
        }
    }

    override suspend fun searchCountries(query: String): DataResult<List<Country>> {
        return when (val result = countryApi.searchCountries(query)) {
            is NetworkResult.Success -> {
                DataResult.Success(
                    result.data.entity()
                )
            }

            is NetworkResult.Error -> {
                DataResult.Error(result.error.toDataError())
            }
        }
    }
}
