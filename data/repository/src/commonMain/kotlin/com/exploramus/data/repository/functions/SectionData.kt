package com.exploramus.data.repository.functions

import com.exploramus.core.models.Country
import com.exploramus.data.repository.Repository

suspend fun Repository.getCountriesBySectionId(sectionId: String): List<Country> = withRepoContext {
    localDb.getAllCountriesBySectionId(sectionId)
}

suspend fun Repository.getAllCountries(): List<Country> = withRepoContext {
    localDb.getAllCountries()
}

suspend fun Repository.getAllCountriesCount(): Long = withRepoContext {
    localDb.getAllCountriesCount()
}

suspend fun Repository.getCountriesCountBySection(sectionId: String): Long = withRepoContext {
    localDb.getCountriesCountBySection(sectionId)
}

suspend fun Repository.getSections() = withRepoContext {
    localDb.getSections()
}
