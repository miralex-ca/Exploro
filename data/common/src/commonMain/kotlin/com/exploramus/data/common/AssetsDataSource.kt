package com.exploramus.data.common

import com.exploramus.core.common.result.DataResult
import com.exploramus.core.models.Country
import com.exploramus.core.models.CountryDetails

interface AssetsDataSource {
    suspend fun fetchAllCountries(): DataResult<List<Country>>
    suspend fun fetchAllCountryDetails(): DataResult<List<CountryDetails>>
}

interface AssetFileReader {
    fun readFile(fileName: String): String
}