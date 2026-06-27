package com.exploramus.shared.viewmodel.screens.countrydetail

import com.exploramus.core.models.CountryWithDetails
import com.exploramus.shared.viewmodel.core.ScreenState

data class DetailsScreenState (
    val isLoading: Boolean = false,
    val details: CountryDetailsState? = null,
    val isFavorite: Boolean = false
): ScreenState


data class CountryDetailsState(
    val id: String = "",
    val name: String = "",
    val officialName: String = "",
    val flagUrl: String = "",
    val flagAlt: String = "",
    val coatOfArmsUrl: String = "",
    val capital: String = "",
    val continent: String = "",
    val subregion: String = "",
    val languages: List<String> = emptyList(),
    val area: Double = 0.0,
    val population: Long = 0L,
    val currency: String = "",
    val timezones: List<String> = emptyList(),
    val isFavorite: Boolean = false,
    val mapsUrl: String = "",
    val wikiUrl: String = "",
    val capitalLat: Double = 0.0,
    val capitalLng: Double = 0.0
)

fun CountryWithDetails.toDetailsState() = CountryDetailsState(
    id = country.id,
    name = country.name,
    officialName = country.officialName,
    flagUrl = country.flagImage,
    flagAlt = country.flagEmoji,
    coatOfArmsUrl = details?.coatOfArmsUrl ?: "",
    capital = country.capital,
    continent = country.continent,
    subregion = validatedLocation(),
    languages = details?.languages ?: emptyList(),
    area = details?.totalArea ?: 0.0,
    population = details?.population ?: 0L,
    currency = formattedCurrency() ?: "",
    timezones = details?.timezones ?: emptyList(),
    isFavorite = isFavorite,
    mapsUrl = details?.mapsUrl ?: "",
    wikiUrl = details?.wikiUrl  ?: "",
    capitalLat = details?.latitude ?: 0.0,
    capitalLng = details?.longitude ?: 0.0
)

fun CountryWithDetails.formattedCurrency(): String? {
    val details = details ?: return null
    if (details.currencyCode.isBlank() && details.currencyName.isBlank() && details.currencySymbol.isBlank()) return null

    return buildString {
        if (details.currencySymbol.isNotBlank()) append("${details.currencySymbol} ")
        if (details.currencyCode.isNotBlank()) append(details.currencyCode)
        if (details.currencyName.isNotBlank()) append(" · ${details.currencyName}")
    }.trim()
}

fun CountryWithDetails.validatedLocation(): String {
    return country.location.takeIf { it.isNotBlank() } ?: country.name.takeIf { details?.mapsUrl != null } ?: ""
}
