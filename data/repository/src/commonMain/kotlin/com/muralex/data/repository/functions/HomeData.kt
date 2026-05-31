package com.muralex.data.repository.functions

import com.muralex.data.repository.Repository
import com.muralex.data.repository.sources.staticdata.homeSections
import com.muralex.models.HomeSection

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
