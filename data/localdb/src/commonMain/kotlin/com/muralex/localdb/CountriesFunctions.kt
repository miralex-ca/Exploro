package com.muralex.localdb

import appLocalDb.AppLocalDb
import appLocalDb.Countries
import com.muralex.models.Country
import com.muralex.models.CountryUserData


fun AppLocalDb.getCountriesList(): List<Country> {
    return countriesQueries.getCountriesList().executeAsList().map { it.toDomain() }
}

fun Countries.toDomain(): Country = Country(
    id = id,
    name = name,
    officialName = official_name,
    capital = capital,
    region = region,
    population = population,
    flagPngUrl = flag_png_url
)

fun AppLocalDb.setCountriesList(list: List<Country>) {
    countriesQueries.transaction {
        list.forEach {
            countriesQueries.upsertCountry(
                id = it.id,
                name = it.name,
                officialName = it.officialName,
                capital = it.capital,
                region = it.region,
                population = it.population,
                flagPngUrl = it.flagPngUrl
            )
        }
    }
}

fun AppLocalDb.getCountriesWithUserData(): List<CountryUserData> {
    return favoritesQueries.getCountriesWithUserData().executeAsList().map {
        CountryUserData(
            country = Country(
                id = it.id,
                name = it.name,
                officialName = it.official_name,
                capital = it.capital,
                region = it.region,
                population = it.population,
                flagPngUrl = it.flag_png_url
            ),
            isFavorite = it.isFavorite == 1L
        )
    }
}
