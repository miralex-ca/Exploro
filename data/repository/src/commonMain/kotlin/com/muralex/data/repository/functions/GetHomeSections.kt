package com.muralex.data.repository.functions

import com.muralex.data.repository.Repository
import com.muralex.mappers.toListItems
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
        val countries = localDb.getCountriesBySection(continent).toListItems()

        if (countries.size < 3) {
            null
        } else {
            HomeSection(
                sectionId = continent,
                sectionName = continent,
                countries = countries
            )
        }
    }
}