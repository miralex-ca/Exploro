package com.muralex.data.common

import com.muralex.core.common.result.DataResult
import com.muralex.models.Country
import com.muralex.models.CountryDetails

interface AssetsDataSource {
    suspend fun fetchAllCountries(): DataResult<List<Country>>
    suspend fun fetchAllCountryDetails(): DataResult<List<CountryDetails>>
}

interface AssetFileReader {
    fun readFile(fileName: String): String
}