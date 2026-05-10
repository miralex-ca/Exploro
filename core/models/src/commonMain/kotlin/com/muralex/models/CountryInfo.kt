package com.muralex.models

data class Country(
    val id: String,
    val name: String,
    val officialName: String,
    val capital: String,
    val region: String,
    val population: Long,
    val flagPngUrl: String,
    val flagSvgUrl: String,
    val flagDescription: String,
) {

    companion object {

        val Empty = Country(
            id = "",
            name = "",
            officialName = "",
            capital = "",
            region = "",
            population = 0,
            flagPngUrl = "",
            flagSvgUrl = "",
            flagDescription = "",
        )
    }
}



data class CountryUserData(
    val country: Country,
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