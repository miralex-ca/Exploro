package com.muralex.data.common

import com.muralex.models.Country
import com.muralex.models.CountryExtraInfo

interface RemoteDataSource {
    suspend fun fetchCountriesList(): List<Country>?
    suspend fun fetchCountryExtraData(country: String): CountryExtraInfo?
}