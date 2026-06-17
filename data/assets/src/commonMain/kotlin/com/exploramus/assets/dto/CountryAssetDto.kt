package com.exploramus.assets.dto

import com.muralex.models.Country
import com.muralex.models.CountryDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CountryAssetDto(
    @SerialName("id") val id: String = "",
    @SerialName("name") val name: String = "",
    @SerialName("official_name") val officialName: String = "",
    @SerialName("capital") val capital: String = "",
    @SerialName("continent") val continent: String = "",
    @SerialName("subregion") val subregion: String = "",
    @SerialName("flag_png_url") val flagPngUrl: String = "",
    @SerialName("flag_alt") val flagAlt: String = "",
    @SerialName("population") val population: Long = 0,
    @SerialName("area") val area: Double = 0.0,
    @SerialName("coat_of_arms_png_url") val coatOfArmsPngUrl: String = "",
    @SerialName("currency_code") val currencyCode: String = "",
    @SerialName("currency_name") val currencyName: String = "",
    @SerialName("currency_symbol") val currencySymbol: String = "",
    @SerialName("languages") val languages: List<String> = emptyList(),
    @SerialName("maps_google_url") val mapsGoogleUrl: String = "",
    @SerialName("maps_osm_url") val mapsOsmUrl: String = "",
    @SerialName("timezones") val timezones: List<String> = emptyList(),
    @SerialName("wikipedia_url") val wikipediaUrl: String = "",
    @SerialName("capital_lat") val capitalLat: Double = 0.0,
    @SerialName("capital_lng") val capitalLng: Double = 0.0
)

internal fun CountryAssetDto.toCountry() = Country(
    id = id,
    name = name,
    officialName = officialName,
    capital = capital,
    continent = continent,
    subregion = subregion,
    flagPngUrl = flagPngUrl,
    flagAlt = flagAlt
)

internal fun CountryAssetDto.toCountryDetails() = CountryDetails(
    id = id,
    coatOfArmsPngUrl = coatOfArmsPngUrl,
    area = area,
    population = population,
    currencyCode = currencyCode,
    currencyName = currencyName,
    currencySymbol = currencySymbol,
    languages = languages,
    mapsGoogleUrl = mapsGoogleUrl,
    mapsOsmUrl = mapsOsmUrl,
    timezones = timezones,
    wikipediaUrl = wikipediaUrl,
    capitalLat = capitalLat,
    capitalLng = capitalLng
)