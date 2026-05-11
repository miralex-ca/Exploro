package com.muralex.data.functions

import com.muralex.data.Repository
import com.muralex.models.Country
import com.muralex.models.CountryDetails
import com.muralex.models.CountryExtraInfo
import com.muralex.models.CountryInfo
import com.muralex.models.DataResult



suspend fun Repository.getCountryDetails(code: String): CountryDetails? = withRepoContext {
     localDb.getCountryDetailsById(code)
}