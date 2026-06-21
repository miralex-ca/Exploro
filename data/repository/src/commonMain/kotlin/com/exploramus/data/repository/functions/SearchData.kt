package com.exploramus.data.repository.functions

import com.exploramus.data.repository.Repository
import com.exploramus.core.models.Country

suspend fun Repository.searchCountries(query: String): List<Country> = withRepoContext {
    localDb.searchCountries(query)
}

