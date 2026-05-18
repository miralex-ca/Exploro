package com.muralex.mappers

import com.muralex.models.Country
import com.muralex.models.CountryListItem

fun Country.toListItem(): CountryListItem =
    CountryListItem(
        id = id,
        name = name,
        officialName = officialName,
        capital = capital,
        continent = continent,
        subregion = subregion,
        flagPngUrl = flagPngUrl,
    )

fun List<Country>.toListItems():
        List<CountryListItem> = map { it.toListItem() }