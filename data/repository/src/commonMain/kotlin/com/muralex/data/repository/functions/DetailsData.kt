package com.muralex.data.repository.functions

import com.muralex.data.repository.Repository
import com.muralex.models.CountryWithDetails


suspend fun Repository.getCountryDetails(code: String):  CountryWithDetails? = withRepoContext {
     localDb.getCountryDetailsById(code)
}