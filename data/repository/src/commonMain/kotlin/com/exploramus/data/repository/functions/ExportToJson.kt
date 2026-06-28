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
    iso2 =  country.iso2.ifBlank { iso3ToIso2[country.id]?.lowercase() ?: "" },
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

val iso3ToIso2 = mapOf(
    "AFG" to "AF",
    "ALA" to "AX",
    "ALB" to "AL",
    "DZA" to "DZ",
    "ASM" to "AS",
    "AND" to "AD",
    "AGO" to "AO",
    "AIA" to "AI",
    "ATA" to "AQ",
    "ATG" to "AG",
    "ARG" to "AR",
    "ARM" to "AM",
    "ABW" to "AW",
    "AUS" to "AU",
    "AUT" to "AT",
    "AZE" to "AZ",

    "BHS" to "BS",
    "BHR" to "BH",
    "BGD" to "BD",
    "BRB" to "BB",
    "BLR" to "BY",
    "BEL" to "BE",
    "BLZ" to "BZ",
    "BEN" to "BJ",
    "BMU" to "BM",
    "BTN" to "BT",
    "BOL" to "BO",
    "BES" to "BQ",
    "BIH" to "BA",
    "BWA" to "BW",
    "BVT" to "BV",
    "BRA" to "BR",
    "IOT" to "IO",
    "BRN" to "BN",
    "BGR" to "BG",
    "BFA" to "BF",
    "BDI" to "BI",

    "CPV" to "CV",
    "KHM" to "KH",
    "CMR" to "CM",
    "CAN" to "CA",
    "CYM" to "KY",
    "CAF" to "CF",
    "TCD" to "TD",
    "CHL" to "CL",
    "CHN" to "CN",
    "CXR" to "CX",
    "CCK" to "CC",
    "COL" to "CO",
    "COM" to "KM",
    "COG" to "CG",
    "COD" to "CD",
    "COK" to "CK",
    "CRI" to "CR",
    "CIV" to "CI",
    "HRV" to "HR",
    "CUB" to "CU",
    "CUW" to "CW",
    "CYP" to "CY",
    "CZE" to "CZ",

    "DNK" to "DK",
    "DJI" to "DJ",
    "DMA" to "DM",
    "DOM" to "DO",

    "ECU" to "EC",
    "EGY" to "EG",
    "SLV" to "SV",
    "GNQ" to "GQ",
    "ERI" to "ER",
    "EST" to "EE",
    "SWZ" to "SZ",
    "ETH" to "ET",

    "FLK" to "FK",
    "FRO" to "FO",
    "FJI" to "FJ",
    "FIN" to "FI",
    "FRA" to "FR",

    "GUF" to "GF",
    "PYF" to "PF",
    "ATF" to "TF",
    "GAB" to "GA",
    "GMB" to "GM",
    "GEO" to "GE",
    "DEU" to "DE",
    "GHA" to "GH",
    "GIB" to "GI",
    "GRC" to "GR",
    "GRL" to "GL",
    "GRD" to "GD",
    "GLP" to "GP",
    "GUM" to "GU",
    "GTM" to "GT",
    "GGY" to "GG",
    "GIN" to "GN",
    "GNB" to "GW",
    "GUY" to "GY",

    "HTI" to "HT",
    "HMD" to "HM",
    "VAT" to "VA",
    "HND" to "HN",
    "HKG" to "HK",
    "HUN" to "HU",

    "ISL" to "IS",
    "IND" to "IN",
    "IDN" to "ID",
    "IRN" to "IR",
    "IRQ" to "IQ",
    "IRL" to "IE",
    "IMN" to "IM",
    "ISR" to "IL",
    "ITA" to "IT",

    "JAM" to "JM",
    "JPN" to "JP",
    "JEY" to "JE",
    "JOR" to "JO",

    "KAZ" to "KZ",
    "KEN" to "KE",
    "KIR" to "KI",
    "PRK" to "KP",
    "KOR" to "KR",
    "KWT" to "KW",
    "KGZ" to "KG",

    "LAO" to "LA",
    "LVA" to "LV",
    "LBN" to "LB",
    "LSO" to "LS",
    "LBR" to "LR",
    "LBY" to "LY",
    "LIE" to "LI",
    "LTU" to "LT",
    "LUX" to "LU",

    "MAC" to "MO",
    "MDG" to "MG",
    "MWI" to "MW",
    "MYS" to "MY",
    "MDV" to "MV",
    "MLI" to "ML",
    "MLT" to "MT",
    "MHL" to "MH",
    "MTQ" to "MQ",
    "MRT" to "MR",
    "MUS" to "MU",
    "MYT" to "YT",
    "MEX" to "MX",
    "FSM" to "FM",
    "MDA" to "MD",
    "MCO" to "MC",
    "MNG" to "MN",
    "MNE" to "ME",
    "MSR" to "MS",
    "MAR" to "MA",
    "MOZ" to "MZ",
    "MMR" to "MM",
    "NAM" to "NA",
    "NRU" to "NR",
    "NPL" to "NP",
    "NLD" to "NL",
    "NCL" to "NC",
    "NZL" to "NZ",
    "NIC" to "NI",
    "NER" to "NE",
    "NGA" to "NG",
    "NIU" to "NU",
    "NFK" to "NF",
    "MKD" to "MK",
    "MNP" to "MP",
    "NOR" to "NO",

    "OMN" to "OM",

    "PAK" to "PK",
    "PLW" to "PW",
    "PSE" to "PS",
    "PAN" to "PA",
    "PNG" to "PG",
    "PRY" to "PY",
    "PER" to "PE",
    "PHL" to "PH",
    "PCN" to "PN",
    "POL" to "PL",
    "PRT" to "PT",
    "PRI" to "PR",

    "QAT" to "QA",

    "REU" to "RE",
    "ROU" to "RO",
    "RUS" to "RU",
    "RWA" to "RW",

    "BLM" to "BL",
    "SHN" to "SH",
    "KNA" to "KN",
    "LCA" to "LC",
    "MAF" to "MF",
    "SPM" to "PM",
    "VCT" to "VC",
    "WSM" to "WS",
    "SMR" to "SM",
    "STP" to "ST",
    "SAU" to "SA",
    "SEN" to "SN",
    "SRB" to "RS",
    "SYC" to "SC",
    "SLE" to "SL",
    "SGP" to "SG",
    "SXM" to "SX",
    "SVK" to "SK",
    "SVN" to "SI",
    "SLB" to "SB",
    "SOM" to "SO",
    "ZAF" to "ZA",
    "SGS" to "GS",
    "SSD" to "SS",
    "ESP" to "ES",
    "LKA" to "LK",
    "SDN" to "SD",
    "SUR" to "SR",
    "SJM" to "SJ",
    "SWE" to "SE",
    "CHE" to "CH",
    "SYR" to "SY",

    "TWN" to "TW",
    "TJK" to "TJ",
    "TZA" to "TZ",
    "THA" to "TH",
    "TLS" to "TL",
    "TGO" to "TG",
    "TKL" to "TK",
    "TON" to "TO",
    "TTO" to "TT",
    "TUN" to "TN",
    "TUR" to "TR",
    "TKM" to "TM",
    "TCA" to "TC",
    "TUV" to "TV",

    "UGA" to "UG",
    "UKR" to "UA",
    "ARE" to "AE",
    "GBR" to "GB",
    "UMI" to "UM",
    "UNK" to "XK",
    "USA" to "US",
    "URY" to "UY",
    "UZB" to "UZ",
    "VGB" to "VG",
    "VIR" to "VI",
    "VUT" to "VU",
    "VEN" to "VE",
    "VNM" to "VN",

    "WLF" to "WF",
    "ESH" to "EH",

    "YEM" to "YE",

    "ZMB" to "ZM",
    "ZWE" to "ZW"
)
