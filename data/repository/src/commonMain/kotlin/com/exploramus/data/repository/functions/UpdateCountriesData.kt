package com.exploramus.data.repository.functions

import com.exploramus.core.common.logging.Log
import com.exploramus.core.common.result.DataResult
import com.exploramus.data.repository.Repository
import com.exploramus.core.models.Country
import com.exploramus.core.models.CountryDetails
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.datetime.Clock


suspend fun Repository.updateCountriesListData(forceUpdate: Boolean = false): DataResult<Unit> =
    withRepoContext {
        val nowUnixTime = Clock.System.now().epochSeconds
        val tenDays = 10 * 24 * 3600L
        val apiSyncExpired = nowUnixTime - localSettings.apiSyncTimestamp > tenDays
        val dbEmpty = !localDb.hasCountriesData()

        if (!dbEmpty && !apiSyncExpired && !forceUpdate) {
            Log.d("App data is up to date")
            return@withRepoContext DataResult.Success(Unit)
        }

        if (forceUpdate && !apiSyncExpired && !dbEmpty) {
            Log.d("Skipping force update")
            return@withRepoContext DataResult.Success(Unit)
        }

        Log.d("Updating app data: forceUpdate - $forceUpdate , apiSyncExpired - $apiSyncExpired, dbEmpty - $dbEmpty")

        val apiResult = if (apiSyncExpired || dbEmpty) {
            webservices.fetchAllCountriesData()
        } else null

        if (apiResult is DataResult.Success) {
            val (countries, details) = apiResult.data
            val coatOfArmsMap = buildCoatOfArmsMap()
            val detailsWithCoats = details.map { detail ->
                if (detail.coatOfArmsPngUrl.isBlank()) {
                    detail.copy(coatOfArmsPngUrl = coatOfArmsMap[detail.id].orEmpty())
                } else detail
            }
            storeCountries(countries, detailsWithCoats)
            localSettings.apiSyncTimestamp = nowUnixTime
            localSettings.listCacheTimestamp = nowUnixTime
            Log.d("App data has beed successfully updated from API")
            return@withRepoContext DataResult.Success(Unit)
        }

        if (dbEmpty) {
            Log.d("API failed or was skipped — use assets only if DB is empty")
            return@withRepoContext seedFromAssets(nowUnixTime)
        }

        Log.d("DB has data, API failed — return success, use existing DB data")
        return@withRepoContext DataResult.Success(Unit)
    }

private suspend fun Repository.buildCoatOfArmsMap(): Map<String, String> {
    val result = assetsDataSource.fetchAllCountryDetails()
    return if (result is DataResult.Success) {
        result.data.associate { it.id to it.coatOfArmsPngUrl }
    } else emptyMap()
}

private suspend fun Repository.storeCountries(
    countries: List<Country>,
    details: List<CountryDetails>
): Boolean {
    val countriesStored = runCatching {
        localDb.setCountriesList(countries.sortedBy { it.name })
    }.isSuccess
    val detailsStored = runCatching {
        localDb.setCountryDetailsList(details)
    }.isSuccess
    return countriesStored && detailsStored
}

private suspend fun Repository.seedFromAssets(nowUnixTime: Long): DataResult<Unit> {
    val countriesResult = assetsDataSource.fetchAllCountries()
    val detailsResult = assetsDataSource.fetchAllCountryDetails()

    val stored = if (countriesResult is DataResult.Success && detailsResult is DataResult.Success) {
        storeCountries(countriesResult.data, detailsResult.data)
    } else false

    return if (stored) {
        localSettings.listCacheTimestamp = nowUnixTime
        // intentionally NOT setting apiSyncTimestamp — assets don't count as API sync
        DataResult.Success(Unit)
    } else {
        DataResult.Error(error = null)
    }
}
