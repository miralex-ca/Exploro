package com.exploramus.app.previews

import com.exploramus.core.models.Country
import com.exploramus.shared.viewmodel.screens.countrydetail.CountryDetailsState

object PreviewCountry {
    val france = Country(
        id = "FRA",
        iso2 = "FR",
        name = "France",
        officialName = "French Republic",
        capital = "Paris",
        continent = "Europe",
        location = "Western Europe",
        flagImage = "https://flags.restcountries.com/v5/w640/fr.png",
        flagEmoji = "🇫🇷"
    )

    val mockList = listOf(
        france,
        Country(
            id = "DEU",
            iso2 = "DE",
            name = "Germany",
            officialName = "Federal Republic of Germany",
            capital = "Berlin",
            continent = "Europe",
            location = "Western Europe",
            flagImage = "https://flags.restcountries.com/v5/w640/de.png",
            flagEmoji = "🇩🇪"
        ),
        Country(
            id = "ITA",
            iso2 = "IT",
            name = "Italy",
            officialName = "Italian Republic",
            capital = "Rome",
            continent = "Europe",
            location = "Southern Europe",
            flagImage = "https://flags.restcountries.com/v5/w640/it.png",
            flagEmoji = "🇮🇹"
        )
    )
}

object PreviewCountryDetailsState {
    val france = CountryDetailsState(
        id = "FRA",
        name = "France",
        officialName = "French Republic",
        flagUrl = "https://flags.restcountries.com/v5/w640/fr.png",
        flagAlt = "🇫🇷",
        coatOfArmsUrl = "https://mainfacts.com/media/images/coats_of_arms/fr.png",
        capital = "Paris",
        continent = "Europe",
        subregion = "Western Europe",
        languages = listOf("French"),
        area = 551695.0,
        population = 69081996,
        currency = "Euro (€)",
        timezones = listOf("UTC+01:00"),
        isFavorite = false,
        mapsUrl = "https://goo.gl/maps/g7QxxSFsWyTPKuzd7",
        wikiUrl = "https://en.wikipedia.org/wiki/France",
        capitalLat = 48.87,
        capitalLng = 2.33
    )

    val empty = CountryDetailsState(
        id = "",
        name = "",
        officialName = "",
        flagUrl = "",
        flagAlt = "",
        coatOfArmsUrl = "",
        capital = "",
        continent = "",
        subregion = "",
        languages = emptyList(),
        area = 0.0,
        population = 0,
        currency = "",
        timezones = emptyList(),
        isFavorite = false,
        mapsUrl = "",
        wikiUrl = "",
        capitalLat = 0.0,
        capitalLng = 0.0
    )
}
