package com.muralex.data.functions

import com.muralex.data.Repository
import com.muralex.models.CountryExtraInfo
import com.muralex.models.CountryInfo

suspend fun Repository.getCountryInfo(country: String): CountryInfo = withRepoContext {

    if (!runtimeCache.countryExtraData.containsKey(country)) {
        webservices.fetchCountryExtraData(country)?.also {
            runtimeCache.countryExtraData[country] = it
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