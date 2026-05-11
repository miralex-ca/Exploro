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

    @SerialName("currencies")
    val currencies: Map<String, CurrencyDto> = emptyMap(),

    @SerialName("capital")
    val capital: List<String> = emptyList(),

    @SerialName("region")
    val region: String = "",

    @SerialName("subregion")
    val subregion: String = "",

    @SerialName("population")
    val population: Long = 0,
) {

    val entity: Country
        get() {
            val currency = currencies.values.firstOrNull()

            return Country(
                id = cca3,
                name = name.common,
                officialName = name.official,
                capital = capital.firstOrNull().orEmpty(),
                region = region,
                subregion = subregion,
                population = population,
                flagPngUrl = flags.png,
                currencyName = currency?.name.orEmpty(),
                currencySymbol = currency?.symbol.orEmpty(),
            )
        }
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

@Serializable
data class CurrencyDto(
    @SerialName("name")
    val name: String = "",

    @SerialName("symbol")
    val symbol: String = "",
)

fun List<CountryDto>.entity() =
    map(CountryDto::entity)



