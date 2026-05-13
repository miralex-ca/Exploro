package com.muralex.network.dto

import com.muralex.models.Country
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryDto(
    @SerialName("cca3")
    val cca3: String = "",

    @SerialName("name")
    val name: CountryNameDto = CountryNameDto(),

    @SerialName("flags")
    val flags: CountryFlagsDto = CountryFlagsDto(),

    @SerialName("capital")
    val capital: List<String> = emptyList(),

    @SerialName("continents")
    val continents: List<String> = emptyList(),

    @SerialName("subregion")
    val subregion: String = ""
) {

    val entity: Country
        get() = Country(
            id = cca3,
            name = name.common,
            officialName = name.official,
            capital = capital.firstOrNull().orEmpty(),
            continent = continents.firstOrNull().orEmpty(),
            subregion = subregion,
            flagPngUrl = flags.png,
            flagAlt = flags.alt
        )
}

@Serializable
data class CountryNameDto(

    @SerialName("common")
    val common: String = "",

    @SerialName("official")
    val official: String = "",
)

@Serializable
data class CountryFlagsDto(

    @SerialName("png")
    val png: String = "",

    @SerialName("alt")
    val alt: String = "",
)


fun List<CountryDto>.entity() =
    map(CountryDto::entity)



