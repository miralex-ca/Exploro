package com.muralex.models

data class Country(
    val id: String,                 // cca3
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

    val timezones: List<String>
)

data class CountryFull(
    val country: Country,
    val details: CountryDetails?,
    val isFavorite: Boolean = false
)



data class CountryUserData(
    val country: CountryListItem,
    val isFavorite: Boolean = false
)


data class CountryListItem(
    val id: String,
    val name: String,
    val officialName: String,
    val capital: String,
    val continent: String,
    val subregion: String,
    val flagPngUrl: String,
) {

    companion object {
        val Empty = CountryListItem(
            id = "",
            name = "",
            officialName = "",
            capital = "",
            continent = "",
            subregion = "",
            flagPngUrl = "",
        )
    }
}
