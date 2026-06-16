package com.exploramus.assets

import com.exploramus.assets.dto.CountryAssetDto
import com.exploramus.assets.dto.toCountry
import com.exploramus.assets.dto.toCountryDetails
import com.muralex.core.common.result.DataError
import com.muralex.core.common.result.DataResult
import com.muralex.data.common.AssetFileReader
import com.muralex.data.common.AssetsDataSource
import com.muralex.models.Country
import com.muralex.models.CountryDetails
import kotlinx.serialization.json.Json

private const val FALLBACK_FILE = "countries_fallback.json"

class JsonAssetsDataSource(
    private val fileReader: AssetFileReader
) : AssetsDataSource {

    private val json = Json { ignoreUnknownKeys = true }

    private fun loadCountries(): List<CountryAssetDto> =
        runCatching {
            val raw = fileReader.readFile(FALLBACK_FILE)
            val parsed = json.decodeFromString<List<CountryAssetDto>>(raw)
            println("Asset file has been parsed: ${parsed.size} items")
            parsed
        }.getOrElse { emptyList() }

    override suspend fun fetchAllCountries(): DataResult<List<Country>> =
        runCatching {
            DataResult.Success(loadCountries().map { it.toCountry() })
        }.getOrElse {
            DataResult.Error(DataError.ReadAsset)
        }

    override suspend fun fetchAllCountryDetails(): DataResult<List<CountryDetails>> =
        runCatching {
            DataResult.Success(loadCountries().map { it.toCountryDetails() })
        }.getOrElse {
            DataResult.Error(DataError.ReadAsset)
        }
}