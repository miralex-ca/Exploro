package com.muralex.data.functions

import com.muralex.data.Repository
import com.muralex.models.Country
import com.muralex.models.DataResult

import kotlinx.datetime.Clock

suspend fun Repository.getCountriesListData(): List<Country> = withRepoContext {
    val nowUnixTime = Clock.System.now().epochSeconds

    if (nowUnixTime - localSettings.listCacheTimestamp > 360 * 60) {

        when (val result = webservices.fetchCountries()) {
            is DataResult.Success -> {
                localDb.setCountriesList(result.data.sortedBy { it.name })
                localSettings.listCacheTimestamp = nowUnixTime
            }
            is DataResult.Error -> {
               // debugLogger.log("ERROR MESSAGE: fetch failed")
            }
        }

    }

    localDb.getCountriesList()
}

suspend fun Repository.getFavoriteCountries(): List<Country> = withRepoContext {

    // LOCAL DB operation, to toggle a country as favorite
//    if (alsoToggleCountry != null) {
//        localDb.toggleFavoriteCountry(alsoToggleCountry)
//    }

   // localDb.getCountriesWithUserData().mapNotNull { if (it.isFavorite) it.country else null }
    emptyList()
}