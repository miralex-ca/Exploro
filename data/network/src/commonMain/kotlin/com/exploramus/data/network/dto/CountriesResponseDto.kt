package com.exploramus.data.network.dto

import com.exploramus.core.models.CountryInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CountriesResponseDto(
    @SerialName("data") val data: CountriesDataDto
)

@Serializable
data class CountriesDataDto(
    @SerialName("objects") val objects: List<CountryRawDto>,
    @SerialName("meta") val meta: CountriesMetaDto
)

@Serializable
data class CountriesMetaDto(
    @SerialName("total") val total: Int
)

@Serializable
data class CountryRawDto(
    @SerialName("id") val id: String = "",
    @SerialName("languages") val languages: List<String> = emptyList()
)

fun CountryRawDto.toCountryInfo() = CountryInfo(
    id = id,
    languages = languages
)
