package com.muralex.network

import com.muralex.core.common.result.DataResult
import com.muralex.data.common.RemoteDataSource
import com.muralex.models.Country
import com.muralex.models.CountryDetails
import com.muralex.network.api.CountryApi
import com.muralex.network.dto.entity
import com.muralex.network.dto.toCountry
import com.muralex.network.dto.toCountryDetails

class RemoteDataSourceImpl(
    private val countryApi: CountryApi
) : RemoteDataSource {

    override suspend fun fetchAllCountriesData(): DataResult<Pair<List<Country>, List<CountryDetails>>> {
        return when (val result = countryApi.fetchAllRaw()) {
            is NetworkResult.Success -> {
                val raw = result.data
                    .filter { it.codes.alpha3.isNotBlank() }
                    .distinctBy { it.codes.alpha3 }
                DataResult.Success(
                    Pair(
                        raw.map { it.toCountry() },
                        raw.map { it.toCountryDetails() }
                    )
                )
            }
            is NetworkResult.Error -> DataResult.Error(result.error.toDataError())
        }
    }

    override suspend fun fetchAllCountries(): DataResult<List<Country>> {
        return DataResult.Error(null)
    }

    override suspend fun fetchAllCountryDetails(): DataResult<List<CountryDetails>> {
        return DataResult.Error(null)
    }
}
