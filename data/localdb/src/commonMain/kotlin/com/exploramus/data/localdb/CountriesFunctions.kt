package com.exploramus.data.localdb

import appLocalDb.AppLocalDb
import appLocalDb.Countries
import com.exploramus.core.models.Country
import com.exploramus.core.models.CountryDetails

fun AppLocalDb.setCountriesList(list: List<Country>) {
    countriesQueries.transaction {
        list.forEach {
            countriesQueries.upsertCountry(
                id = it.id,
                name = it.name,
                officialName = it.officialName,
                capital = it.capital,
                continent = it.continent,
                subregion = it.subregion,
                flagPngUrl = it.flagPngUrl,
                flagAlt = it.flagAlt
            )
        }
    }
}

fun AppLocalDb.setCountriesDetailsList(list: List<CountryDetails>) {
    countryDetailsQueries.transaction {
        list.forEach {
            countryDetailsQueries.upsertCountryDetails(
                countryId = it.id,

                population = it.population,
                area = it.area,

                coatOfArmsPngUrl = it.coatOfArmsPngUrl,

                currencyCode = it.currencyCode,
                currencyName = it.currencyName,
                currencySymbol = it.currencySymbol,

                languages = it.languages.joinToString(","),

                mapsGoogleUrl = it.mapsGoogleUrl,
                mapsOsmUrl = it.mapsOsmUrl,

                timezones = it.timezones.joinToString(","),
                wikipediaUrl = it.wikipediaUrl,
                capitalLat = it.capitalLat,
                capitalLng = it.capitalLng
            )
        }
    }
}

fun AppLocalDb.getCountryDetailsById(
    id: String
) = countriesQueries
    .getCountryDetailsById(id)
    .executeAsOneOrNull()


fun AppLocalDb.getCountriesByContinent(
    continent: String,
    limit: Long = 12
): List<Countries> {
    return countriesQueries
        .getCountriesByContinent(
            continent = continent,
            limit = limit
        )
        .executeAsList()
}

fun AppLocalDb.getAllCountriesBySection(
    continent: String
): List<Countries> {
    return countriesQueries
        .getAllCountriesByContinent(
            continent = continent
        )
        .executeAsList()
}


fun AppLocalDb.getFavorites(): List<Countries> {
    return favoritesQueries
        .getFavorites()
        .executeAsList()
}

fun AppLocalDb.searchCountries(query: String): List<Countries> {
    val q = query.trim()
    if (q.isBlank()) return emptyList()

    return countriesQueries.searchCountries(q, q, q)
        .executeAsList()
}

fun AppLocalDb.getCountriesCount(): Long {
    return countriesQueries.countCountries().executeAsOne()
}

fun AppLocalDb.getAllCountriesWithDetails() = countriesQueries
    .getAllCountriesWithDetails()
    .executeAsList()




