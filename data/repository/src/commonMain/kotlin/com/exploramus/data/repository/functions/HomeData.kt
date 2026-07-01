package com.exploramus.data.repository.functions

import com.exploramus.core.models.HomeSection
import com.exploramus.data.repository.Repository

private const val MIN_COUNTRIES_IN_SECTION = 3

suspend fun Repository.getHomeSections(): List<HomeSection> = withRepoContext {
    localDb.getSections().mapNotNull { section ->
        val countries = localDb.getCountriesBySection(section.id)

        if (countries.size < MIN_COUNTRIES_IN_SECTION) {
            null
        } else {
            HomeSection(
                sectionId = section.id,
                sectionName = section.name,
                countries = countries
            )
        }
    }
}
