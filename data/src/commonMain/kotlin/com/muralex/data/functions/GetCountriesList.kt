package com.muralex.data.functions

import com.muralex.data.Repository
import com.muralex.data.debugLogger
import com.muralex.data.objects.entity
import com.muralex.data.sources.localdb.getCountriesList
import com.muralex.data.sources.localdb.setCountriesList
import com.muralex.data.sources.webservices.apis.fetchCountriesList
import com.muralex.models.Country
import kotlinx.datetime.Clock

suspend fun Repository.getCountriesListData(): List<Country> = withRepoContext {
    val nowUnixTime = Clock.System.now().epochSeconds

    if (nowUnixTime-localSettings.listCacheTimestamp > 60*60) {
        webservices.fetchCountriesList()?.apply {
            if (error==null) {
                localDb.setCountriesList(data.entity().sortedBy { it.name })
                localSettings.listCacheTimestamp = nowUnixTime
            } else {
                debugLogger.log ("ERROR MESSAGE: $error")
            }
        }
    }

    localDb.getCountriesList()
}