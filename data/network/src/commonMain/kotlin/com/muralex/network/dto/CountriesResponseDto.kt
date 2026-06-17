package com.muralex.network.dto

import com.muralex.models.Country
import com.muralex.models.CountryDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountriesResponseDto(
    @SerialName("data")
    val data: CountriesDataDto
)

@Serializable
data class CountriesDataDto(
    @SerialName("objects")
    val objects: List<CountryRawDto>,
    @SerialName("meta")
    val meta: CountriesMetaDto
)

@Serializable
data class CountriesMetaDto(
    @SerialName("total") val total: Int,
    @SerialName("count") val count: Int,
    @SerialName("limit") val limit: Int,
    @SerialName("offset") val offset: Int,
    @SerialName("more") val more: Boolean
)

@Serializable
data class CountryRawDto(
    @SerialName("codes") val codes: CountryCodesDto = CountryCodesDto(),
    @SerialName("names") val names: CountryNamesDto = CountryNamesDto(),
    @SerialName("flag") val flag: CountryFlagDto = CountryFlagDto(),
    @SerialName("capitals") val capitals: List<CountryCapitalDto> = emptyList(),
    @SerialName("continents") val continents: List<String> = emptyList(),
    @SerialName("subregion") val subregion: String = "",
    @SerialName("area") val area: CountryAreaDto = CountryAreaDto(),
    @SerialName("population") val population: Long = 0,
    @SerialName("languages") val languages: List<CountryLanguageDto> = emptyList(),
    @SerialName("currencies") val currencies: List<CountryCurrencyDto> = emptyList(),
    @SerialName("links") val links: CountryLinksDto = CountryLinksDto(),
    @SerialName("timezones") val timezones: List<String> = emptyList(),
)

@Serializable
data class CountryCodesDto(
    @SerialName("alpha_3") val alpha3: String = ""
)

@Serializable
data class CountryNamesDto(
    @SerialName("common") val common: String = "",
    @SerialName("official") val official: String = ""
)

@Serializable
data class CountryFlagDto(
    @SerialName("emoji") val emoji: String = "",
    @SerialName("url_png") val urlPng: String = "",
    @SerialName("url_svg") val urlSvg: String = ""
)

@Serializable
data class CountryCapitalDto(
    @SerialName("name") val name: String = ""
)

@Serializable
data class CountryAreaDto(
    @SerialName("kilometers") val kilometers: Double = 0.0
)

@Serializable
data class CountryLanguageDto(
    @SerialName("iso639_3") val iso639_3: String = "",
    @SerialName("name") val name: String = "",
    @SerialName("native_name") val nativeName: String = ""
)

@Serializable
data class CountryCurrencyDto(
    @SerialName("code") val code: String = "",
    @SerialName("name") val name: String = "",
    @SerialName("symbol") val symbol: String = ""
)

@Serializable
data class CountryLinksDto(
    @SerialName("google_maps") val googleMaps: String = "",
    @SerialName("open_street_maps") val openStreetMaps: String = "",
    @SerialName("wikipedia") val wikipedia: String = ""
)


fun CountryRawDto.toCountry() = Country(
    id = codes.alpha3,
    name = names.common,
    officialName = names.official,
    capital = capitals.firstOrNull()?.name.orEmpty(),
    continent = continents.firstOrNull().orEmpty(),
    subregion = subregion,
    flagPngUrl = flag.urlPng,
    flagAlt = flag.emoji
)

fun CountryRawDto.toCountryDetails() = CountryDetails(
    id = codes.alpha3,
    coatOfArmsPngUrl = "",  // not in v5 — filled from assets later
    area = area.kilometers,
    population = population,
    currencyCode = currencies.firstOrNull()?.code.orEmpty(),
    currencyName = currencies.firstOrNull()?.name.orEmpty(),
    currencySymbol = currencies.firstOrNull()?.symbol.orEmpty(),
    languages = languages.map { it.name },
    mapsGoogleUrl = links.googleMaps,
    mapsOsmUrl = links.openStreetMaps,
    timezones = timezones,
    wikipediaUrl = links.wikipedia
)