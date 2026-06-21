package com.exploramus.data.network.dto

import com.exploramus.core.models.CountryDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryDetailsDto(

    @SerialName("cca3")
    val cca3: String = "",

    @SerialName("coatOfArms")
    val coatOfArms: CountryImageDto = CountryImageDto(),

    @SerialName("currencies")
    val currencies: Map<String, CurrencyDto> = emptyMap(),

    @SerialName("languages")
    val languages: Map<String, String> = emptyMap(),

    @SerialName("population")
    val population: Long = 0,

    @SerialName("area")
    val area: Double = 0.0,

    @SerialName("maps")
    val maps: CountryMapsDto = CountryMapsDto(),

    @SerialName("timezones")
    val timezones: List<String> = emptyList(),
) {

    val entity: CountryDetails
        get() {

            val currencyCode = currencies.keys.firstOrNull().orEmpty()
            val currency = currencies[currencyCode]

            return CountryDetails(
                id = cca3,

                coatOfArmsPngUrl = coatOfArms.validPng().orEmpty(),

                area = area,
                population = population,

                currencyCode = currencyCode,
                currencyName = currency?.name.orEmpty(),
                currencySymbol = currency?.symbol.orEmpty(),

                languages = languages.values.toList(),

                mapsGoogleUrl = maps.googleMaps,
                mapsOsmUrl = maps.openStreetMaps,

                timezones = timezones
            )
        }
}

fun List<CountryDetailsDto>.entity() =
    map(CountryDetailsDto::entity)

@Serializable
data class CountryMapsDto(
    @SerialName("googleMaps")
    val googleMaps: String = "",

    @SerialName("openStreetMaps")
    val openStreetMaps: String = ""
)

@Serializable
data class CountryImageDto(
    @SerialName("png")
    val png: String = "",

    @SerialName("svg")
    val svg: String = ""
) {
    fun validPng(): String? =
        png.takeIf { it.isNotBlank() }
}

@Serializable
data class CoatOfArmsDto(
    @SerialName("png")
    val png: String = "",

    @SerialName("svg")
    val svg: String = ""
) {
    fun validPng(): String? =
        png.takeIf { it.isNotBlank() }
}

@Serializable
data class CurrencyDto(

    @SerialName("name")
    val name: String = "",

    @SerialName("symbol")
    val symbol: String = "",
)