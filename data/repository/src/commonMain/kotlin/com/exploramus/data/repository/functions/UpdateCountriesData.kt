package com.exploramus.data.repository.functions

import com.exploramus.core.common.logging.Log
import com.exploramus.core.common.result.DataResult
import com.exploramus.core.models.Country
import com.exploramus.core.models.CountryDetails
import com.exploramus.data.repository.Repository
import kotlin.time.Clock


suspend fun Repository.updateCountriesListData(forceUpdate: Boolean = false): DataResult<Unit> =
    withRepoContext {
        val nowUnixTime: Long = Clock.System.now().toEpochMilliseconds()
        val apiDataExpireTime: Long = 3 * 24 * 3600L * 1000L
        val apiSyncExpired = (nowUnixTime - localSettings.apiSyncTimestamp) > apiDataExpireTime
        val dbEmpty = !localDb.hasCountriesData()

        if (!dbEmpty && !apiSyncExpired && !forceUpdate) {
            Log.d("App data is up to date")
            return@withRepoContext DataResult.Success(Unit)
        }

        // remove this if force sync from settings is required
//        if (forceUpdate && !apiSyncExpired && !dbEmpty) {
//            Log.d("Skipping force update")
//            return@withRepoContext DataResult.Success(Unit)
//        }

        val countriesResult = assetsDataSource.readAllCountries()
        val detailsResult = assetsDataSource.readAllCountryDetails()
        val sectionsResult = assetsDataSource.readAllSections()

        if (countriesResult !is DataResult.Success || detailsResult !is DataResult.Success || sectionsResult !is DataResult.Success) {
            Log.d("Assets failed — cannot proceed")
            return@withRepoContext DataResult.Error(error = null)
        }

        val countries = countriesResult.data
        var details = detailsResult.data
        val sections = sectionsResult.data

        if (apiSyncExpired || forceUpdate) {
            val apiResult = webservices.fetchAllCountriesData()
            if (apiResult is DataResult.Success) {
                val languagesMap = apiResult.data.associate { it.id to it.languages }
                details = details.map { detail ->
                    val languages = languagesMap[detail.id]
                    if (!languages.isNullOrEmpty()) detail.copy(languages = languages) else detail
                }
                localSettings.apiSyncTimestamp = nowUnixTime
                Log.d("Languages merged from API")
            } else {
                Log.d("API failed — storing assets as-is")
            }
        }

        val stored = storeCountries(countries, details, sections)
        return@withRepoContext if (stored) {
            localSettings.dataCacheTimestamp = nowUnixTime
            Log.d("App data successfully updated")
            DataResult.Success(Unit)
        } else {
            DataResult.Error(error = null)
        }
    }


private suspend fun Repository.storeCountries(
    countries: List<Country>,
    details: List<CountryDetails>,
    sections: List<com.exploramus.core.models.Section>
): Boolean {
    val countriesStored = runCatching {
        localDb.setCountriesList(countries.sortedBy { it.name })
    }.isSuccess
    val detailsStored = runCatching {
        localDb.setCountryDetailsList(details)
    }.isSuccess
    val sectionsStored = runCatching {
        localDb.setSections(sections)
    }.isSuccess
    return countriesStored && detailsStored && sectionsStored
}
