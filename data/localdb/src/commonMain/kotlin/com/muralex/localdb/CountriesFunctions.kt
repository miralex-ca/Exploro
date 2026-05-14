package com.muralex.localdb

import appLocalDb.AppLocalDb
import com.muralex.models.Country
import com.muralex.models.CountryDetails
import com.muralex.models.CountryListItem
import com.muralex.models.CountryUserData


fun AppLocalDb.getCountriesList(): List<Country> {
    return countriesQueries
        .getCountriesList()
        .executeAsList()
        .map { it.toDomain() }
}

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

fun AppLocalDb.getCountriesWithUserData(): List<CountryUserData> {
    return favoritesQueries
        .getCountriesWithUserData()
        .executeAsList()
        .map {

            CountryUserData(
                country = CountryListItem(
                    id = it.id,
                    name = it.name,
                    officialName = it.official_name,
                    capital = it.capital,
                    continent = it.continent,
                    subregion = it.subregion,
                    flagPngUrl = it.flag_png_url,
                ),
                isFavorite = it.isFavorite == 1L
            )
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

                timezones = it.timezones.joinToString(",")
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
): List<Country> {
    return countriesQueries
        .getCountriesByContinent(
            continent = continent,
            limit = limit
        )
        .executeAsList()
        .map { it.toDomain() }
}

fun AppLocalDb.getAllCountriesByContinent(
    continent: String
): List<Country> {

    return countriesQueries
        .getAllCountriesByContinent(
            continent = continent
        )
        .executeAsList()
        .map { it.toDomain() }
}



