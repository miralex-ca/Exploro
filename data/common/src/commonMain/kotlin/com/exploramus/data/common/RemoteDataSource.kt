package com.exploramus.data.common

import com.exploramus.core.common.result.DataResult
import com.exploramus.core.models.Country
import com.exploramus.core.models.CountryDetails

interface RemoteDataSource {
    suspend fun fetchAllCountriesData(): DataResult<Pair<List<Country>, List<CountryDetails>>>
    suspend fun fetchAllCountries(): DataResult<List<Country>>
    suspend fun fetchAllCountryDetails(): DataResult<List<CountryDetails>>
}