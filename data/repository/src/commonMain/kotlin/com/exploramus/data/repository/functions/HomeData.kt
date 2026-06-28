package com.exploramus.data.repository.functions

import com.exploramus.core.models.HomeSection
import com.exploramus.data.repository.Repository
import com.exploramus.data.repository.sources.staticdata.homeSections

private const val MIN_COUNTRIES_IN_SECTION = 3

suspend fun Repository.getHomeSections(): List<HomeSection> = withRepoContext {
    homeSections.mapNotNull { section ->
        val countries = localDb.getCountriesBySection(section.name)

        if (countries.size < MIN_COUNTRIES_IN_SECTION) {
            null
        } else {
            HomeSection(
                sectionId = section.name,
                sectionName = section.name,
                countries = countries
            )
        }
    }
}
