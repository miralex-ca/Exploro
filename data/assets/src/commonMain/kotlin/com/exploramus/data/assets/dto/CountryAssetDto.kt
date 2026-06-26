package com.exploramus.data.assets.dto

import com.exploramus.core.models.Country
import com.exploramus.core.models.CountryDetails
import com.exploramus.data.assets.data.iso3ToIso2
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
    @SerialName("capitalLat") val capitalLat: Double = 0.0,
    @SerialName("capitalLng") val capitalLng: Double = 0.0
)

internal fun CountryAssetDto.toCountry() = Country(
    id = id,
    name = name,
    officialName = officialName,
    capital = capital,
    continent = continent,
    subregion = subregion,
    flagPngUrl = buildFlagPngUrl(iso3 = id),
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

private const val FLAG_BASE_URL =
    "https://flagcdn.com"

internal fun buildFlagPngUrl(iso3: String, size: Int = 640): String {
    val iso2 = iso3ToIso2[iso3]?.lowercase()
    return "$FLAG_BASE_URL/w$size/$iso2.png"
}


// DTO for countries_data.json
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

// DTO for countries_detail_data.json
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
    name = name,
    officialName = officialName,
    capital = capital,
    continent = continent,
    subregion = location,       // location → subregion
    flagPngUrl = flagImage,     // already a full URL in the new JSON
    flagAlt = flagEmoji,        // emoji as alt, or swap for a string if you have one
)

internal fun CountryDetailAssetDto.toCountryDetails() = CountryDetails(
    id = id,
    population = population,
    area = totalArea,
    coatOfArmsPngUrl = coatOfArmsUrl,
    currencyCode = currencyCode,
    currencyName = currencyName,
    currencySymbol = currencySymbol,
    languages = languages,
    mapsGoogleUrl = mapsUrl,
    mapsOsmUrl = "",
    timezones = timezones,
    wikipediaUrl = wikiUrl,
    capitalLat = latitude,
    capitalLng = longitude,
)