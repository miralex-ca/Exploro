package com.exploramus.data.repository.functions

import com.exploramus.core.models.Country
import com.exploramus.data.repository.Repository

suspend fun Repository.getCountriesBySectionId(sectionId: String): List<Country> = withRepoContext {
    localDb.getAllCountriesBySectionId(sectionId)
}