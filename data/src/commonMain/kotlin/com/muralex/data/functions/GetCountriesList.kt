package com.muralex.data.functions

import com.muralex.data.Repository
import com.muralex.models.Country

import kotlinx.datetime.Clock

suspend fun Repository.getCountriesListData(): List<Country> = withRepoContext {
    val nowUnixTime = Clock.System.now().epochSeconds

    if (nowUnixTime-localSettings.listCacheTimestamp > 60*60) {
        val countries = webservices.fetchCountries()
        if (countries != null) {
            localDb.setCountriesList(countries.sortedBy { it.name })
            localSettings.listCacheTimestamp = nowUnixTime
        } else {
          //  debugLogger.log("ERROR MESSAGE: fetch failed")
        }
    }

    localDb.getCountriesList()
}

suspend fun Repository.getFavoriteCountries(): List<Country> = withRepoContext {

    // LOCAL DB operation, to toggle a country as favorite
//    if (alsoToggleCountry != null) {
//        localDb.toggleFavoriteCountry(alsoToggleCountry)
//    }

    localDb.getCountriesWithUserData().mapNotNull { if (it.isFavorite) it.country else null }
}

sealed class DataResult<out T> {
    data class Success<T>(val data: T) : DataResult<T>()
    data class Error(val message: String) : DataResult<Nothing>()
}