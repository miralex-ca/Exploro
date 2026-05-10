package com.muralex.data.common

import com.muralex.models.Country
import com.muralex.models.CountryExtraInfo

interface RemoteDataSource {
    suspend fun fetchCountries(): List<Country>
    suspend fun fetchCountryDetails(code: String): Country?
    suspend fun searchCountries(query: String): List<Country>
}