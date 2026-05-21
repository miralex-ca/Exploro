package com.muralex.data.repository.functions

import com.muralex.core.common.result.DataResult
import com.muralex.data.repository.Repository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.datetime.Clock


suspend fun Repository.updateCountriesListData(): DataResult<Unit> = withRepoContext {
        val nowUnixTime = Clock.System.now().epochSeconds
        val shouldRefresh = nowUnixTime - localSettings.listCacheTimestamp > 24 * 3600

        if (!shouldRefresh) {
            return@withRepoContext  DataResult.Success(Unit)
        }

        coroutineScope {
            val countriesDeferred = async { webservices.fetchAllCountries() }
            val detailsDeferred = async { webservices.fetchAllCountryDetails() }

            val countriesResult = countriesDeferred.await()
            val detailsResult = detailsDeferred.await()

            val countriesStored =
                if (countriesResult is DataResult.Success) {
                    runCatching {
                        localDb.setCountriesList(countriesResult.data.sortedBy { it.name })
                    }.isSuccess
                } else {
                    false
                }

            val detailsStored =
                if (detailsResult is DataResult.Success) {
                    runCatching {
                        localDb.setCountryDetailsList(detailsResult.data)
                    }.isSuccess
                } else {
                    false
                }
            when {
                countriesStored && detailsStored -> {
                    localSettings.listCacheTimestamp = nowUnixTime
                    DataResult.Success(Unit)
                }

                else -> {
                    DataResult.Error(error = null)
                }
            }
        }
    }
