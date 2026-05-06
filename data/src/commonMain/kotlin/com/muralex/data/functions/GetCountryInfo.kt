package com.muralex.data.functions

import com.muralex.data.Repository
import com.muralex.data.sources.localdb.getCountriesList
import com.muralex.data.sources.webservices.apis.fetchCountryExtraData
import com.muralex.models.CountryExtraInfo
import com.muralex.models.CountryInfo

suspend fun Repository.getCountryInfo(country: String): CountryInfo = withRepoContext {

    if (!runtimeCache.countryExtraData.containsKey(country)) {
        webservices.fetchCountryExtraData(country)?.apply {
            runtimeCache.countryExtraData[country] = data
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