package com.exploramus.data.network

import com.exploramus.core.common.result.DataResult
import com.exploramus.core.models.CountryInfo
import com.exploramus.data.common.RemoteDataSource
import com.exploramus.data.network.api.CountryApi
import com.exploramus.data.network.dto.toCountryInfo

class RemoteDataSourceImpl(
    private val countryApi: CountryApi
) : RemoteDataSource {

    override suspend fun fetchAllCountriesData(): DataResult<List<CountryInfo>> {
        return when (val result = countryApi.fetchExample()) {
            is NetworkResult.Success -> {
                val raw = result.data
                    .filter { it.id.isNotBlank() }
                    .distinctBy { it.id }
                DataResult.Success(raw.map { it.toCountryInfo() })
            }
            is NetworkResult.Error -> DataResult.Error(result.error.toDataError())
        }
    }
}
