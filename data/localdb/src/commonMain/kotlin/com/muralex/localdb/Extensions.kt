package com.muralex.localdb

import appLocalDb.Countries
import appLocalDb.GetCountryDetailsById
import com.muralex.models.Country
import com.muralex.models.CountryDetails
import com.muralex.models.CountryFull

fun Countries.toDomain(): Country =
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



fun GetCountryDetailsById.toDomain(): CountryFull =
    CountryFull(
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
                    ?: emptyList()
            )
        } else {
            null
        },

        isFavorite = isFavorite == 1L
    )