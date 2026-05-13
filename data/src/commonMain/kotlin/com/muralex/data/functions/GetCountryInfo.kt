package com.muralex.data.functions

import com.muralex.data.Repository
import com.muralex.models.CountryFull


suspend fun Repository.getCountryDetails(code: String):  CountryFull? = withRepoContext {
     localDb.getCountryDetailsById(code)
}