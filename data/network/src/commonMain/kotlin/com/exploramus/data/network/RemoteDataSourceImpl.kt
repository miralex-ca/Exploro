package com.exploramus.data.network

import com.exploramus.core.common.result.DataResult
import com.exploramus.core.models.Country
import com.exploramus.core.models.CountryDetails
import com.exploramus.data.common.RemoteDataSource
import com.exploramus.data.network.api.CountryApi
import com.exploramus.data.network.dto.toCountry
import com.exploramus.data.network.dto.toCountryDetails

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
}
