package com.muralex.localdb

import appLocalDb.AppLocalDb
import com.muralex.models.Country
import com.muralex.models.CountryUserData

fun AppLocalDb.getCountriesList(): List<Country> {
    return countriesQueries.getCountriesList { _, name, population ->
        Country(name = name, population = population)
    }.executeAsList()
}

fun AppLocalDb.setCountriesList(list: List<Country>) {
    countriesQueries.transaction {
        list.forEach {
            countriesQueries.upsertCountry(
                id = it.id,
                name = it.name,
                population = it.population,
            )
        }
    }
}

fun AppLocalDb.getCountriesWithUserData(): List<CountryUserData> {
    return favoritesQueries.getCountriesWithUserData().executeAsList()
        .map {
            CountryUserData(
                country = Country(id = it.id, name = it.name, population = it.population),
                isFavorite = it.isFavorite == 1L
            )
        }
}
