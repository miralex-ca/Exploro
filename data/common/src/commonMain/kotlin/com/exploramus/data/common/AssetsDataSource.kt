package com.exploramus.data.common

import com.exploramus.core.common.result.DataResult
import com.exploramus.core.models.Country
import com.exploramus.core.models.CountryDetails

interface AssetsDataSource {
    suspend fun readAllCountries(): DataResult<List<Country>>
    suspend fun readAllCountryDetails(): DataResult<List<CountryDetails>>
}

interface AssetFileReader {
    fun readFile(fileName: String): String
}