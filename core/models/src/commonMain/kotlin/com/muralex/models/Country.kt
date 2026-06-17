package com.muralex.models

data class Country(
    val id: String,
    val name: String,
    val officialName: String,
    val capital: String,
    val continent: String,
    val subregion: String,
    val flagPngUrl: String,
    val flagAlt: String,
)


data class CountryDetails(
    val id: String,
    val coatOfArmsPngUrl: String,
    val area: Double,
    val population: Long,

    val currencyCode: String,
    val currencyName: String,
    val currencySymbol: String,

    val languages: List<String>,

    val mapsGoogleUrl: String,
    val mapsOsmUrl: String,

    val timezones: List<String>,
    val wikipediaUrl: String = "",
    val capitalLat: Double = 0.0,
    val capitalLng: Double = 0.0
)


data class CountryWithDetails(
    val country: Country,
    val details: CountryDetails?,
    val isFavorite: Boolean = false
)
