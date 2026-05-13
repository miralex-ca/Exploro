package com.muralex.data.functions

import com.muralex.data.Repository
import com.muralex.data.utils.repoDebugLogger
import com.muralex.models.DataResult
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.datetime.Clock


suspend fun Repository.updateCountriesListDatas() = withRepoContext {
    val nowUnixTime = Clock.System.now().epochSeconds

    if (nowUnixTime - localSettings.listCacheTimestamp > 360 * 60) {
        when (val result = webservices.fetchAllCountries()) {
            is DataResult.Success -> {
                localDb.setCountriesList(result.data.sortedBy { it.name })
                localSettings.listCacheTimestamp = nowUnixTime
            }
            is DataResult.Error -> {
              repoDebugLogger.log("ERROR MESSAGE: fetch failed")
            }
        }
    }
}

suspend fun Repository.updateCountriesListData() = withRepoContext {

    val nowUnixTime = Clock.System.now().epochSeconds

    val shouldRefresh = nowUnixTime - localSettings.listCacheTimestamp > 360 * 60

    if (!shouldRefresh) return@withRepoContext

    coroutineScope {

        val countriesDeferred = async {
            webservices.fetchAllCountries()
        }

        val detailsDeferred = async {
            webservices.fetchAllCountryDetails()
        }

        val countriesResult = countriesDeferred.await()
        val detailsResult = detailsDeferred.await()

        when (countriesResult) {
            is DataResult.Success -> {
                localDb.setCountriesList(
                    countriesResult.data.sortedBy { it.name }
                )
            }

            is DataResult.Error -> {
                repoDebugLogger.log("ERROR: countries fetch failed")
            }
        }

        when (detailsResult) {
            is DataResult.Success -> {
                localDb.setCountryDetailsList(detailsResult.data)
            }

            is DataResult.Error -> {
                repoDebugLogger.log("ERROR: details fetch failed")
            }
        }


        if (countriesResult is DataResult.Success || detailsResult is DataResult.Success) {
            localSettings.listCacheTimestamp = nowUnixTime
        }
    }
}