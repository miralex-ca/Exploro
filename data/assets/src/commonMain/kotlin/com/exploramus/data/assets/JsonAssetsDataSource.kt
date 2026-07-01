package com.exploramus.data.assets

import com.exploramus.core.common.result.DataResult
import com.exploramus.core.models.Country
import com.exploramus.core.models.CountryDetails
import com.exploramus.core.models.Section
import com.exploramus.data.assets.dto.*
import com.exploramus.data.common.AssetFileReader
import com.exploramus.data.common.AssetsDataSource
import kotlinx.serialization.json.Json

internal const val COUNTRIES_DATA_FILE = "countries_data.json"
internal const val COUNTRIES_DETAIL_DATA_FILE = "countries_detail_data.json"
internal const val SECTIONS_DATA_FILE = "sections.json"

class JsonAssetsDataSource(
    private val fileReader: AssetFileReader
) : AssetsDataSource {

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun readAllCountries(): DataResult<List<Country>> =
        runCatching {
            val raw = fileReader.readFile(COUNTRIES_DATA_FILE)
            val parsed = json.decodeFromString<List<CountryDataAssetDto>>(raw)
            DataResult.Success(parsed.map { it.toCountry() })
        }.getOrElse {
            DataResult.Error(error = null)
        }

    override suspend fun readAllCountryDetails(): DataResult<List<CountryDetails>> =
        runCatching {
            val raw = fileReader.readFile(COUNTRIES_DETAIL_DATA_FILE)
            val parsed = json.decodeFromString<List<CountryDetailAssetDto>>(raw)
            DataResult.Success(parsed.map { it.toCountryDetails() })
        }.getOrElse {
            DataResult.Error(error = null)
        }

    override suspend fun readAllSections(): DataResult<List<Section>> =
        runCatching {
            val raw = fileReader.readFile(SECTIONS_DATA_FILE)
            val parsed = json.decodeFromString<List<SectionAssetDto>>(raw)
            DataResult.Success(parsed.map { it.toSection() })
        }.getOrElse {
            DataResult.Error(error = null)
        }
}