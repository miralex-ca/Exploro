package com.exploramus.data.repository.functions

import com.exploramus.data.repository.Repository
import com.exploramus.core.models.CountryWithDetails


suspend fun Repository.getCountryDetails(code: String):  CountryWithDetails? = withRepoContext {
     localDb.getCountryDetailsById(code)
}