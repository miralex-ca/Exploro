package com.muralex.data.repository.functions

import com.muralex.data.repository.Repository
import com.muralex.models.Country
import com.muralex.models.CountryDetails
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

suspend fun Repository.exportToJson(): String = withRepoContext {
    val countries = localDb.getAllCountriesWithDetails()
    val json = Json { prettyPrint = true }
    json.encodeToString(
        countries.mapNotNull { (country, details) ->
            details?.let { CountryExportDto(country, it) }
        }
    )
}

@Serializable
private data class CountryExportDto(
    val id: String,
    val name: String,
    val official_name: String,
    val capital: String,
    val continent: String,
    val subregion: String,
    val flag_png_url: String,
    val flag_alt: String,
    val population: Long,
    val area: Double,
    val coat_of_arms_png_url: String,
    val currency_code: String,
    val currency_name: String,
    val currency_symbol: String,
    val languages: List<String>,
    val maps_google_url: String,
    val maps_osm_url: String,
    val timezones: List<String>,
    val wikipedia_url: String
)

private fun CountryExportDto(
    country: Country,
    details: CountryDetails
) = CountryExportDto(
    id = country.id,
    name = country.name,
    official_name = country.officialName,
    capital = country.capital,
    continent = country.continent,
    subregion = country.subregion,
    flag_png_url = country.flagPngUrl,
    flag_alt = country.flagAlt,
    population = details.population,
    area = details.area,
    coat_of_arms_png_url = details.coatOfArmsPngUrl,
    currency_code = details.currencyCode,
    currency_name = details.currencyName,
    currency_symbol = details.currencySymbol,
    languages = details.languages,
    maps_google_url = details.mapsGoogleUrl,
    maps_osm_url = details.mapsOsmUrl,
    timezones = details.timezones,
    wikipedia_url = details.wikipediaUrl
)

