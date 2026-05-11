package com.muralex.models

data class Country(
    val id: String,
    val name: String,
    val officialName: String,
    val capital: String,
    val region: String,
    val subregion: String,
    val population: Long,
    val flagPngUrl: String,
    val currencyName: String,
    val currencySymbol: String,
) {

    companion object {
        val Empty = Country(
            id = "",
            name = "",
            officialName = "",
            capital = "",
            region = "",
            subregion = "",
            population = 0,
            flagPngUrl = "",
            currencyName = "",
            currencySymbol = "",
        )
    }
}



data class CountryUserData(
    val country: CountryListItem,
    val isFavorite: Boolean = false
)

data class CountryInfo (
    val _listData : Country = Country.Empty,
    val _extraData : CountryExtraInfo? = CountryExtraInfo(),
) {
    val population = _listData.population.toString()
}

data class CountryExtraInfo (
    val vaccines : String = "",
) {
    val vaccinesList : List<String>
        get() = vaccines.split(", ")
}


data class CountriesListItem (
    val _data :  Country,
) {
    val name = _data.name
}

data class CountryListItem(
    val id: String,
    val name: String,
    val officialName: String,
    val capital: String,
    val region: String,
    val subregion: String,
    val flagPngUrl: String,
) {

    companion object {

        val Empty = CountryListItem(
            id = "",
            name = "",
            officialName = "",
            capital = "",
            region = "",
            subregion = "",
            flagPngUrl = "",
        )
    }
}

fun Country.toListItem(): CountryListItem = CountryListItem(
    id = id,
    name = name,
    officialName = officialName,
    capital = capital,
    region = region,
    subregion = subregion,
    flagPngUrl = flagPngUrl,
)