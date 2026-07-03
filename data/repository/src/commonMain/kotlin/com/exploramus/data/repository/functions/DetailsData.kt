package com.exploramus.data.repository.functions

import com.exploramus.core.models.CountryWithDetails
import com.exploramus.data.repository.Repository


suspend fun Repository.getCountryDetails(code: String):  CountryWithDetails? = withRepoContext {
     localDb.getCountryDetailsById(code)
}

suspend fun Repository.getCountriesWithDetailsBySection(section: String) = withRepoContext {
     localDb.getAllCountriesWithDetailsBySectionId(section)
}

suspend fun Repository.getFavoritesWithDetails() = withRepoContext {
     localDb.getFavoritesWithDetails()
}