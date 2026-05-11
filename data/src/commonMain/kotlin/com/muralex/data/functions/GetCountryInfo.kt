package com.muralex.data.functions

import com.muralex.data.Repository
import com.muralex.models.Country
import com.muralex.models.CountryExtraInfo
import com.muralex.models.CountryInfo
import com.muralex.models.DataResult

suspend fun Repository.getCountryInfo(country: String): CountryInfo = withRepoContext {

    if (!runtimeCache.countryExtraData.containsKey(country)) {
        webservices.fetchCountryDetails(country)?.also {
           // runtimeCache.countryExtraData[country] = it
        }
    }

    val listData = localDb.getCountriesList().first { it.name == country }
    val extraData = runtimeCache.countryExtraData[country]

    CountryInfo(
        _listData = listData,
        _extraData = extraData?.let {
            CountryExtraInfo(vaccines = it.vaccines)
        }
    )
}

suspend fun Repository.getCountryDetails(code: String): Country? = withRepoContext {

    when (val result = webservices.fetchCountryDetails(code = code)) {
        is DataResult.Success -> {
            result.data
        }
        else -> null

    }

}