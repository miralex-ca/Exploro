package com.exploramus.data.assets

import com.exploramus.core.common.result.DataResult
import com.exploramus.core.models.Country
import com.exploramus.core.models.CountryDetails
import com.exploramus.data.assets.dto.CountryAssetDto
import com.exploramus.data.assets.dto.toCountry
import com.exploramus.data.assets.dto.toCountryDetails
import com.exploramus.data.common.AssetFileReader
import com.exploramus.data.common.AssetsDataSource
import kotlinx.serialization.json.Json

internal const val FALLBACK_FILE = "countries_fallback.json"

class JsonAssetsDataSource(
    private val fileReader: AssetFileReader
) : AssetsDataSource {

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun fetchAllCountries(): DataResult<List<Country>> =
        runCatching {
            val raw = fileReader.readFile(FALLBACK_FILE)
            val parsed = json.decodeFromString<List<CountryAssetDto>>(raw)
            DataResult.Success(parsed.map { it.toCountry() })
        }.getOrElse {
            DataResult.Error(error = null)
        }

    override suspend fun fetchAllCountryDetails(): DataResult<List<CountryDetails>> =
        runCatching {
            val raw = fileReader.readFile(FALLBACK_FILE)
            val parsed = json.decodeFromString<List<CountryAssetDto>>(raw)
            DataResult.Success(parsed.map { it.toCountryDetails() })
        }.getOrElse {
            DataResult.Error(error = null)
        }
}