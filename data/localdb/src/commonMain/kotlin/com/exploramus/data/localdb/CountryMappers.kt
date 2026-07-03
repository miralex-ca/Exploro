package com.exploramus.data.localdb

import appLocalDb.Countries
import appLocalDb.GetAllCountriesWithDetails
import appLocalDb.GetAllCountriesWithDetailsByContinent
import appLocalDb.GetCountryDetailsById
import appLocalDb.GetFavoritesWithDetails
import com.exploramus.core.models.Country
import com.exploramus.core.models.CountryDetails
import com.exploramus.core.models.CountryWithDetails

private fun Countries.toCountry(): Country =
    Country(
        id = id,
        iso2 = iso2,
        name = name,
        officialName = official_name,
        capital = capital,
        continent = continent,
        location = location,
        flagImage = flag_image,
        flagEmoji = flag_emoji
    )

fun List<Countries>.toCountryList() = map { it.toCountry() }

fun GetCountryDetailsById.toCountryWithDetails(): CountryWithDetails =
    CountryWithDetails(
        country = Country(
            id = id,
            iso2 = iso2,
            name = name,
            officialName = official_name,
            capital = capital,
            continent = continent,
            location = location,
            flagImage = flag_image,
            flagEmoji = flag_emoji
        ),

        details = if (population != null) {
            CountryDetails(
                id = id,
                coatOfArmsUrl = coat_of_arms_url.orEmpty(),
                totalArea = total_area ?: 0.0,
                population = population,
                currencyCode = currency_code.orEmpty(),
                currencyName = currency_name.orEmpty(),
                currencySymbol = currency_symbol.orEmpty(),
                languages = languages
                    ?.split(",")
                    ?.filter { it.isNotBlank() }
                    ?: emptyList(),
                mapsUrl = maps_url.orEmpty(),
                timezones = timezones
                    ?.split(",")
                    ?.filter { it.isNotBlank() }
                    ?: emptyList(),
                wikiUrl = wiki_url.orEmpty(),
                latitude = latitude ?: 0.0,
                longitude = longitude ?: 0.0
            )
        } else {
            null
        },

        isFavorite = isFavorite == 1L
    )

fun GetAllCountriesWithDetails.toCountryWithDetails(): CountryWithDetails =
    CountryWithDetails(
        country = Country(
            id = id,
            iso2 = iso2,
            name = name,
            officialName = official_name,
            capital = capital,
            continent = continent,
            location = location,
            flagImage = flag_image,
            flagEmoji = flag_emoji
        ),
        details = if (population != null) {
            CountryDetails(
                id = id,
                coatOfArmsUrl = coat_of_arms_url.orEmpty(),
                totalArea = total_area ?: 0.0,
                population = population,
                currencyCode = currency_code.orEmpty(),
                currencyName = currency_name.orEmpty(),
                currencySymbol = currency_symbol.orEmpty(),
                languages = languages?.split(",")?.filter { it.isNotBlank() } ?: emptyList(),
                mapsUrl = maps_url.orEmpty(),
                timezones = timezones?.split(",")?.filter { it.isNotBlank() } ?: emptyList(),
                wikiUrl = wiki_url.orEmpty(),
                latitude = latitude ?: 0.0,
                longitude = longitude ?: 0.0
            )
        } else null,
        isFavorite = isFavorite == 1L
    )

fun GetAllCountriesWithDetailsByContinent.toCountryWithDetails(): CountryWithDetails =
    CountryWithDetails(
        country = Country(
            id = id,
            iso2 = iso2,
            name = name,
            officialName = official_name,
            capital = capital,
            continent = continent,
            location = location,
            flagImage = flag_image,
            flagEmoji = flag_emoji
        ),
        details = if (population != null) {
            CountryDetails(
                id = id,
                coatOfArmsUrl = coat_of_arms_url.orEmpty(),
                totalArea = total_area ?: 0.0,
                population = population,
                currencyCode = currency_code.orEmpty(),
                currencyName = currency_name.orEmpty(),
                currencySymbol = currency_symbol.orEmpty(),
                languages = languages?.split(",")?.filter { it.isNotBlank() } ?: emptyList(),
                mapsUrl = maps_url.orEmpty(),
                timezones = timezones?.split(",")?.filter { it.isNotBlank() } ?: emptyList(),
                wikiUrl = wiki_url.orEmpty(),
                latitude = latitude ?: 0.0,
                longitude = longitude ?: 0.0
            )
        } else null,
        isFavorite = isFavorite == 1L
    )

fun GetFavoritesWithDetails.toCountryWithDetails(): CountryWithDetails =
    CountryWithDetails(
        country = Country(
            id = id,
            iso2 = iso2,
            name = name,
            officialName = official_name,
            capital = capital,
            continent = continent,
            location = location,
            flagImage = flag_image,
            flagEmoji = flag_emoji
        ),
        details = if (population != null) {
            CountryDetails(
                id = id,
                coatOfArmsUrl = coat_of_arms_url.orEmpty(),
                totalArea = total_area ?: 0.0,
                population = population,
                currencyCode = currency_code.orEmpty(),
                currencyName = currency_name.orEmpty(),
                currencySymbol = currency_symbol.orEmpty(),
                languages = languages?.split(",")?.filter { it.isNotBlank() } ?: emptyList(),
                mapsUrl = maps_url.orEmpty(),
                timezones = timezones?.split(",")?.filter { it.isNotBlank() } ?: emptyList(),
                wikiUrl = wiki_url.orEmpty(),
                latitude = latitude ?: 0.0,
                longitude = longitude ?: 0.0
            )
        } else null,
        isFavorite = isFavorite == 1L
    )
