package com.muralex.data.common

import com.muralex.core.common.result.DataResult
import com.muralex.models.Country
import com.muralex.models.CountryDetails

interface RemoteDataSource {
    suspend fun fetchAllCountries(): DataResult<List<Country>>
    suspend fun fetchAllCountryDetails(): DataResult<List<CountryDetails>>
    suspend fun fetchCountryDetails(code: String): DataResult<Country>
    suspend fun searchCountries(query: String): DataResult<List<Country>>
}