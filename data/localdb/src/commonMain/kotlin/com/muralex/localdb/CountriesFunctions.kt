package com.muralex.localdb

import appLocalDb.AppLocalDb
import appLocalDb.Countries
import com.muralex.models.Country
import com.muralex.models.CountryDetails
import com.muralex.models.CountryListItem
import com.muralex.models.CountryUserData


fun AppLocalDb.getCountriesList(): List<Country> {
    return countriesQueries.getCountriesList().executeAsList().map { it.toDomain() }
}

fun AppLocalDb.setCountriesList(list: List<Country>) {
    countriesQueries.transaction {
        list.forEach {
            countriesQueries.upsertCountry(
                id = it.id,
                name = it.name,
                officialName = it.officialName,
                capital = it.capital,
                region = it.region,
                subregion = it.subregion,
                population = it.population,
                flagPngUrl = it.flagPngUrl,
                currencyName = it.currencyName,
                currencySymbol = it.currencySymbol,
            )
        }
    }
}

fun AppLocalDb.getCountriesWithUserData(): List<CountryUserData> {
    return favoritesQueries.getCountriesWithUserData().executeAsList().map {
        CountryUserData(
            country = CountryListItem(
                id = it.id,
                name = it.name,
                officialName = it.official_name,
                capital = it.capital,
                region = it.region,
                subregion = it.subregion,
                flagPngUrl = it.flag_png_url,
            ),
            isFavorite = it.isFavorite == 1L
        )
    }
}

fun AppLocalDb.getCountryDetailsById(
    id: String
) = countriesQueries
        .getCountryDetailsById(id)
         .executeAsOneOrNull()



