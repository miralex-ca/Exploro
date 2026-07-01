package com.exploramus.data.repository.functions

import com.exploramus.core.models.Country
import com.exploramus.core.models.CountryDetails
import com.exploramus.data.repository.Repository
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

suspend fun Repository.exportDataToJson(): Pair<String, String> = withRepoContext {
    val countries = localDb.getAllCountriesWithDetails()
    val json = Json { prettyPrint = true }

    val countriesJson = json.encodeToString(
        ListSerializer(CountryDto.serializer()),
        countries.map { CountryDto(it.country) }
    )

    val detailsJson = json.encodeToString(
        ListSerializer(CountryDetailsDto.serializer()),
        countries.mapNotNull { it.details?.let { details -> CountryDetailsDto(it.country.id, details) } }
    )

    Pair(countriesJson, detailsJson)
}

@Serializable
private data class CountryDto(
    val id: String,
    val iso2: String,
    val name: String,
    val official_name: String,
    val capital: String,
    val continent: String,
    val location: String,
    val flag_image: String,
    val flag_emoji: String,
)

@Serializable
private data class CountryDetailsDto(
    val id: String,
    val population: Long,
    val total_area: Double,
    val coat_of_arms_url: String,
    val currency_name: String,
    val currency_symbol: String,
    val currency_code: String,
    val languages: List<String>,
    val latitude: Double,
    val longitude: Double,
    val timezones: List<String>,
    val maps_url: String,
    val wiki_url: String,
)

private fun CountryDto(country: Country) = CountryDto(
    id = country.id,
    iso2 =  country.iso2,
    name = country.name,
    official_name = country.officialName,
    capital = country.capital,
    continent = country.continent,
    location = country.location,
    flag_image = country.flagImage,
    flag_emoji = country.flagEmoji,
)

private fun CountryDetailsDto(id: String, details: CountryDetails) = CountryDetailsDto(
    id = id,
    population = details.population.toHumanReadableRoundedValue(),
    total_area = details.totalArea.toFormattedAreaRoundedValue(),
    coat_of_arms_url = details.coatOfArmsUrl,
    currency_code = details.currencyCode,
    currency_name = details.currencyName,
    currency_symbol = details.currencySymbol,
    languages = details.languages,
    maps_url = details.mapsUrl,
    timezones = details.timezones,
    wiki_url = details.wikiUrl,
    latitude = details.latitude,
    longitude = details.longitude
)

private fun Double.roundTo1Decimal() =
    kotlin.math.round(this * 10) / 10.0

fun Double.toFormattedAreaRoundedValue(): Double {
    return when {
        this >= 1_000_000 ->
            (this / 1_000_000).roundTo1Decimal() * 1_000_000

        this >= 1_000 ->
            (this / 1_000).roundTo1Decimal() * 1_000

        else ->
            this.toInt().toDouble()
    }
}


private data class Bucket(val divisor: Long, val multiplier: Long)

private val buckets = listOf(
    Bucket(1_000_000_000, 1_000_000_000),
    Bucket(1_000_000, 1_000_000),
    Bucket(1_000, 1_000)
)

fun Long.toHumanReadableRoundedValue(): Long {
    for (bucket in buckets) {
        if (this >= bucket.divisor) {
            val v = this / bucket.divisor.toDouble()
            val rounded = kotlin.math.round(v * 10) / 10
            return (rounded * bucket.multiplier).toLong()
        }
    }
    return this
}