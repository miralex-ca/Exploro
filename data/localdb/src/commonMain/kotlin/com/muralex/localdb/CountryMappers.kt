package com.muralex.localdb

import appLocalDb.Countries
import appLocalDb.GetAllCountriesWithDetails
import appLocalDb.GetCountryDetailsById
import com.muralex.models.Country
import com.muralex.models.CountryDetails
import com.muralex.models.CountryWithDetails

private fun Countries.toCountry(): Country =
    Country(
        id = id,
        name = name,
        officialName = official_name,
        capital = capital,
        continent = continent,
        subregion = subregion,
        flagPngUrl = flag_png_url,
        flagAlt = flag_alt
    )

fun List<Countries>.toCountryList() = map { it.toCountry() }

fun GetCountryDetailsById.toCountryWithDetails(): CountryWithDetails =
    CountryWithDetails(
        country = Country(
            id = id,
            name = name,
            officialName = official_name,
            capital = capital,
            continent = continent,
            subregion = subregion,
            flagPngUrl = flag_png_url,
            flagAlt = flag_alt
        ),

        details = if (population != null) {
            CountryDetails(
                id = id,

                coatOfArmsPngUrl = coat_of_arms_png_url.orEmpty(),

                area = area ?: 0.0,
                population = population,

                currencyCode = currency_code.orEmpty(),
                currencyName = currency_name.orEmpty(),
                currencySymbol = currency_symbol.orEmpty(),

                languages = languages
                    ?.split(",")
                    ?.filter { it.isNotBlank() }
                    ?: emptyList(),

                mapsGoogleUrl = maps_google_url.orEmpty(),
                mapsOsmUrl = maps_osm_url.orEmpty(),

                timezones = timezones
                    ?.split(",")
                    ?.filter { it.isNotBlank() }
                    ?: emptyList(),
                wikipediaUrl = wikipedia_url.orEmpty()
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
            name = name,
            officialName = official_name,
            capital = capital,
            continent = continent,
            subregion = subregion,
            flagPngUrl = flag_png_url,
            flagAlt = flag_alt
        ),
        details = if (population != null) {
            CountryDetails(
                id = id,
                coatOfArmsPngUrl = coat_of_arms_png_url.orEmpty(),
                area = area ?: 0.0,
                population = population,
                currencyCode = currency_code.orEmpty(),
                currencyName = currency_name.orEmpty(),
                currencySymbol = currency_symbol.orEmpty(),
                languages = languages?.split(",")?.filter { it.isNotBlank() } ?: emptyList(),
                mapsGoogleUrl = maps_google_url.orEmpty(),
                mapsOsmUrl = maps_osm_url.orEmpty(),
                timezones = timezones?.split(",")?.filter { it.isNotBlank() } ?: emptyList(),
                wikipediaUrl = wikipedia_url.orEmpty()
            )
        } else null,
        isFavorite = false // not needed
    )