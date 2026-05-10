package com.muralex.network

import com.muralex.data.common.RemoteDataSource
import com.muralex.models.AppError
import com.muralex.models.Country
import com.muralex.models.DataResult
import com.muralex.network.api.CountryApi
import com.muralex.network.dto.entity
import kotlinx.io.IOException

class RemoteDataSourceImpl(
    private val countryApi: CountryApi
) : RemoteDataSource {

    override suspend fun fetchCountries(): DataResult<List<Country>> {
        return when (val result = countryApi.fetchCountries()) {
            is NetworkResult.Success -> {
                DataResult.Success(
                    result.data.entity()
                )
            }

            is NetworkResult.Error -> {
                DataResult.Error(result.toAppError())
            }
        }
    }

    override suspend fun fetchCountryDetails(code: String): DataResult<Country> {
        return when (val result = countryApi.fetchCountryDetails(code)) {
            is NetworkResult.Success -> {
                DataResult.Success(result.data.entity)
            }

            is NetworkResult.Error -> {
                DataResult.Error(result.toAppError())
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
                DataResult.Error(result.toAppError())
            }
        }
    }
}
