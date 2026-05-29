package com.muralex.exploramus.viewmodel.screens.countrydetail

import com.muralex.exploramus.viewmodel.ScreenState
import com.muralex.models.CountryWithDetails

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
)

fun CountryWithDetails.toDetailsState() = CountryDetailsState(
    id = country.id,
    name = country.name,
    officialName = country.officialName,
    flagUrl = country.flagPngUrl,
    flagAlt = country.flagAlt,
    coatOfArmsUrl = details?.coatOfArmsPngUrl ?: "",
    capital = country.capital,
    continent = country.continent,
    subregion = validatedLocation(),
    languages = details?.languages ?: emptyList(),
    area = details?.area ?: 0.0,
    population = details?.population ?: 0L,
    currency = formattedCurrency() ?: "",
    timezones = details?.timezones ?: emptyList(),
    isFavorite = isFavorite,
    mapsUrl = details?.mapsGoogleUrl ?: "",
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
    return country.subregion.takeIf { it.isNotBlank() } ?: country.name.takeIf { details?.mapsGoogleUrl != null } ?: ""
}