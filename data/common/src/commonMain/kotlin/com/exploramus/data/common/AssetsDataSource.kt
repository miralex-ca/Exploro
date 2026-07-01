package com.exploramus.data.common

import com.exploramus.core.common.result.DataResult
import com.exploramus.core.models.Country
import com.exploramus.core.models.CountryDetails
import com.exploramus.core.models.Section

interface AssetsDataSource {
    suspend fun readAllCountries(): DataResult<List<Country>>
    suspend fun readAllCountryDetails(): DataResult<List<CountryDetails>>
    suspend fun readAllSections(): DataResult<List<Section>>
}

interface AssetFileReader {
    fun readFile(fileName: String): String
}