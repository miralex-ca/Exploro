package com.exploramus.data.assets.dto

import com.exploramus.core.models.Country
import com.exploramus.core.models.CountryDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
internal data class CountryDataAssetDto(
    @SerialName("id") val id: String = "",
    @SerialName("iso2") val iso2: String = "",
    @SerialName("name") val name: String = "",
    @SerialName("official_name") val officialName: String = "",
    @SerialName("capital") val capital: String = "",
    @SerialName("continent") val continent: String = "",
    @SerialName("location") val location: String = "",
    @SerialName("flag_image") val flagImage: String = "",
    @SerialName("flag_emoji") val flagEmoji: String = "",
)


@Serializable
internal data class CountryDetailAssetDto(
    @SerialName("id") val id: String = "",
    @SerialName("population") val population: Long = 0,
    @SerialName("total_area") val totalArea: Double = 0.0,
    @SerialName("coat_of_arms_url") val coatOfArmsUrl: String = "",
    @SerialName("currency_name") val currencyName: String = "",
    @SerialName("currency_symbol") val currencySymbol: String = "",
    @SerialName("currency_code") val currencyCode: String = "",
    @SerialName("languages") val languages: List<String> = emptyList(),
    @SerialName("latitude") val latitude: Double = 0.0,
    @SerialName("longitude") val longitude: Double = 0.0,
    @SerialName("timezones") val timezones: List<String> = emptyList(),
    @SerialName("maps_url") val mapsUrl: String = "",
    @SerialName("wiki_url") val wikiUrl: String = "",
)

internal fun CountryDataAssetDto.toCountry() = Country(
    id = id,
    iso2 = iso2,
    name = name,
    officialName = officialName,
    capital = capital,
    continent = continent,
    location = location,
    flagImage = flagImage,
    flagEmoji = flagEmoji,
)

internal fun CountryDetailAssetDto.toCountryDetails() = CountryDetails(
    id = id,
    population = population,
    totalArea = totalArea,
    coatOfArmsUrl = coatOfArmsUrl,
    currencyName = currencyName,
    currencySymbol = currencySymbol,
    currencyCode = currencyCode,
    languages = languages,
    latitude = latitude,
    longitude = longitude,
    timezones = timezones,
    mapsUrl = mapsUrl,
    wikiUrl = wikiUrl,
)
