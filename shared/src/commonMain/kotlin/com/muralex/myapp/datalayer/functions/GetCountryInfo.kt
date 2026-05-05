package com.muralex.myapp.datalayer.functions

import com.muralex.myapp.datalayer.Repository
import com.muralex.myapp.datalayer.sources.localdb.getCountriesList
import com.muralex.myapp.datalayer.sources.webservices.apis.fetchCountryExtraData
import com.muralex.myapp.viewmodel.screens.countrydetail.CountryInfo

suspend fun Repository.getCountryInfo(country: String): CountryInfo = withRepoContext {

    // WEBSERVICE call, to fetch the country extra data
    //      DATA STORE: runtimeCache
    //      FETCH CONDITION: it's not in the runtimeCache
    if (!runtimeCache.countryExtraData.containsKey(country)) {
        webservices.fetchCountryExtraData(country)?.apply {
            runtimeCache.countryExtraData[country] = data
        }
    }

    // RETURN a CountryInfo object, whose constructor takes 2 datalayer objects:
    //  - CountriesListData (read from the localDb)
    //  - CountriesExtraData (read from the runtimeCache)
    CountryInfo(
        localDb.getCountriesList().first{it.name==country},
        runtimeCache.countryExtraData[country],
    )
}