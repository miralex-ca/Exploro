package com.muralex.data

import com.muralex.core.common.DispatchersProvider
import com.muralex.data.common.LocalDataSource
import com.muralex.data.common.RemoteDataSource
import com.muralex.data.sources.localsettings.MySettings
import com.muralex.data.sources.runtimecache.CacheObjects
import com.muralex.network.createRemoteDataSource
import com.russhwolf.settings.Settings
import kotlinx.coroutines.withContext

class Repository(
    val localDb: LocalDataSource,
    val webservices: RemoteDataSource = createRemoteDataSource(),
    val settings: Settings = Settings(),
    val dispatchers: DispatchersProvider = DispatchersProvider.Base(),
) {

    val localSettings by lazy { MySettings(settings) }
    val runtimeCache get() = CacheObjects

    suspend fun <T> withRepoContext (block: suspend () -> T) : T {
        return withContext(dispatchers.default) {
            block()
        }
    }
}



