package com.muralex.data.functions

import com.muralex.data.Repository
import com.muralex.models.DataResult
import kotlinx.datetime.Clock


suspend fun Repository.updateCountriesListData() = withRepoContext {
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
}