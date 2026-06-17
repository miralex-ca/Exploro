package com.muralex.localdb

import appLocalDb.AppLocalDb
import appLocalDb.Countries
import com.muralex.models.Country
import com.muralex.models.CountryDetails

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


fun AppLocalDb.exportAllCountriesToJson(): String {
    val rows = countriesQueries
        .getAllCountriesWithDetails()
        .executeAsList()

    val sb = StringBuilder()
    sb.append("[\n")

    rows.forEachIndexed { index, row ->
        val languages = row.languages
            ?.split(",")
            ?.filter { it.isNotBlank() }
            ?.joinToString(separator = "\",\"", prefix = "[\"", postfix = "\"]")
            ?: "[]"

        val timezones = row.timezones
            ?.split(",")
            ?.filter { it.isNotBlank() }
            ?.joinToString(separator = "\",\"", prefix = "[\"", postfix = "\"]")
            ?: "[]"

        sb.append("  {\n")
        sb.append("    \"id\": ${row.id.jsonString()},\n")
        sb.append("    \"name\": ${row.name.jsonString()},\n")
        sb.append("    \"official_name\": ${row.official_name.jsonString()},\n")
        sb.append("    \"capital\": ${row.capital.jsonString()},\n")
        sb.append("    \"continent\": ${row.continent.jsonString()},\n")
        sb.append("    \"subregion\": ${row.subregion.jsonString()},\n")
        sb.append("    \"flag_png_url\": ${row.flag_png_url.jsonString()},\n")
        sb.append("    \"flag_alt\": ${row.flag_alt.jsonString()},\n")
        sb.append("    \"population\": ${row.population ?: 0},\n")
        sb.append("    \"area\": ${row.area ?: 0.0},\n")
        sb.append("    \"coat_of_arms_png_url\": ${row.coat_of_arms_png_url.orEmpty().jsonString()},\n")
        sb.append("    \"currency_code\": ${row.currency_code.orEmpty().jsonString()},\n")
        sb.append("    \"currency_name\": ${row.currency_name.orEmpty().jsonString()},\n")
        sb.append("    \"currency_symbol\": ${row.currency_symbol.orEmpty().jsonString()},\n")
        sb.append("    \"languages\": $languages,\n")
        sb.append("    \"maps_google_url\": ${row.maps_google_url.orEmpty().jsonString()},\n")
        sb.append("    \"maps_osm_url\": ${row.maps_osm_url.orEmpty().jsonString()},\n")
        sb.append("    \"timezones\": $timezones,\n")
        sb.append("    \"wikipedia_url\": ${row.wikipedia_url.orEmpty().jsonString()}\n")
        sb.append("  }")

        if (index < rows.lastIndex) sb.append(",")
        sb.append("\n")
    }

    sb.append("]")
    return sb.toString()
}

private fun String.jsonString(): String {
    val escaped = this
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t")
    return "\"$escaped\""
}



