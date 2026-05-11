package com.muralex.localdb

import appLocalDb.Countries
import appLocalDb.GetCountryDetailsById
import com.muralex.models.Country
import com.muralex.models.CountryDetails
import com.muralex.models.CountryListItem

fun Countries.toDomain(): Country = Country(
    id = id,
    name = name,
    officialName = official_name,
    capital = capital,
    region = region,
    subregion = subregion,
    population = population,
    flagPngUrl = flag_png_url,
    currencyName = currency_name,
    currencySymbol = currency_symbol,
)

fun Country.toListItem(): CountryListItem = CountryListItem(
    id = id,
    name = name,
    officialName = officialName,
    capital = capital,
    region = region,
    subregion = subregion,
    flagPngUrl = flagPngUrl,
)

fun List<Country>.toListItems() : List<CountryListItem> = map { it.toListItem() }

fun GetCountryDetailsById.toDetails(): CountryDetails = CountryDetails(
    country = Country(
        id = id,
        name = name,
        officialName = official_name,
        capital = capital,
        region = region,
        subregion = subregion,
        population = population,
        flagPngUrl = flag_png_url,
        currencyName = currency_name,
        currencySymbol = currency_symbol,
    ),
    isFavorite = isFavorite == 1L
)