package com.exploramus.data.common

import com.exploramus.core.common.result.DataResult
import com.exploramus.core.models.CountryInfo

interface RemoteDataSource {
    suspend fun fetchAllCountriesData(): DataResult<List<CountryInfo>>
}