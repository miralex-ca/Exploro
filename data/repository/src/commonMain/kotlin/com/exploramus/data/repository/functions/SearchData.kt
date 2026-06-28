package com.exploramus.data.repository.functions

import com.exploramus.core.models.Country
import com.exploramus.data.repository.Repository

suspend fun Repository.searchCountries(query: String): List<Country> = withRepoContext {
    localDb.searchCountries(query)
}

