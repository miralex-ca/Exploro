package com.muralex.data.common

import com.muralex.core.common.result.DataResult
import com.muralex.models.Country
import com.muralex.models.CountryDetails

interface RemoteDataSource {
    suspend fun fetchAllCountriesData(): DataResult<Pair<List<Country>, List<CountryDetails>>>
    suspend fun fetchAllCountries(): DataResult<List<Country>>
    suspend fun fetchAllCountryDetails(): DataResult<List<CountryDetails>>
}