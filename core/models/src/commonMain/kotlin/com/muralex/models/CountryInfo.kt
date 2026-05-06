package com.muralex.models

data class Country(
    val id: String = "",
    val name: String = "",
    val population: Int = 0,
)

data class CountryUserData(
    val country: Country,
    val isFavorite: Boolean = false
)

data class CountryInfo (
    val _listData : Country = Country(),
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