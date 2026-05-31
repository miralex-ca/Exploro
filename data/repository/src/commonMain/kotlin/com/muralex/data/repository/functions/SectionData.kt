package com.muralex.data.repository.functions

import com.muralex.data.repository.Repository
import com.muralex.models.Country

suspend fun Repository.getCountriesBySectionId(sectionId: String): List<Country> = withRepoContext {
    localDb.getAllCountriesBySectionId(sectionId)
}