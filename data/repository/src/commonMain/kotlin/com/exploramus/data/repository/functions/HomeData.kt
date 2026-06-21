package com.exploramus.data.repository.functions

import com.exploramus.data.repository.Repository
import com.exploramus.data.repository.sources.staticdata.homeSections
import com.exploramus.core.models.HomeSection

suspend fun Repository.getHomeSections(): List<HomeSection> = withRepoContext {
    homeSections.mapNotNull { section ->
        val countries = localDb.getCountriesBySection(section.name)

        if (countries.size < 3) {
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
