package com.exploramus.core.models

data class Country(
    val id: String,
    val iso2: String,
    val name: String,
    val officialName: String,
    val capital: String,
    val continent: String,
    val location: String,
    val flagImage: String,
    val flagEmoji: String,
)

data class CountryDetails(
    val id: String,
    val population: Long,
    val totalArea: Double,
    val coatOfArmsUrl: String,
    val currencyName: String,
    val currencySymbol: String,
    val currencyCode: String,
    val languages: List<String>,
    val latitude: Double,
    val longitude: Double,
    val timezones: List<String>,
    val mapsUrl: String,
    val wikiUrl: String,
)


data class CountryWithDetails(
    val country: Country,
    val details: CountryDetails?,
    val isFavorite: Boolean = false
)

data class CountryInfo(
    val id: String,
    val languages: List<String>,
)
