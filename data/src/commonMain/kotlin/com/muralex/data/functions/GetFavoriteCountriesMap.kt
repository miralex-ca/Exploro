package com.muralex.data.functions

import com.muralex.data.Repository


suspend fun Repository.getFavoriteCountriesMap(alsoToggleCountry : String? = null): Map<String,Boolean> = withRepoContext {

    // LOCAL DB operation, to toggle a country as favorite
//    if (alsoToggleCountry != null) {
//        localDb.toggleFavoriteCountry(alsoToggleCountry)
//    }


    localDb.getFavoriteCountriesMap()
}