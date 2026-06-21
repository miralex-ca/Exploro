package com.exploramus.data.repository.functions

import com.exploramus.data.repository.Repository
import com.exploramus.core.models.Country

suspend fun Repository.getCountriesBySectionId(sectionId: String): List<Country> = withRepoContext {
    localDb.getAllCountriesBySectionId(sectionId)
}