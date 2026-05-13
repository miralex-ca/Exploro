package com.muralex.data.functions

import com.muralex.data.Repository
import com.muralex.models.HomeSection


val homeContinents = listOf(
    "Europe",
    "Asia",
    "Africa",
    "North America",
    "South America",
    "Oceania"
)

suspend fun Repository.getHomeSections(): List<HomeSection> = withRepoContext {
    homeContinents.mapNotNull { continent ->
        val countries = localDb.getCountriesByContinent(continent)

        if (countries.size < 3) {
            null
        } else {
            HomeSection(
                continent = continent,
                countries = countries
            )
        }
    }
}