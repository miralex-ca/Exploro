package com.muralex.data.common

import com.muralex.models.Country
import com.muralex.models.DataResult

interface RemoteDataSource {
    suspend fun fetchCountries(): DataResult<List<Country>>
    suspend fun fetchCountryDetails(code: String): DataResult<Country>
    suspend fun searchCountries(query: String): DataResult<List<Country>>
}