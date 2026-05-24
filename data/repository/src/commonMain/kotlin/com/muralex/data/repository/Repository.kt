package com.muralex.data.repository

import com.muralex.data.repository.sources.localsettings.MySettings
import com.muralex.data.repository.sources.runtimecache.CacheObjects
import com.muralex.core.common.DispatchersProvider
import com.muralex.data.common.LocalDataSource
import com.muralex.data.common.PlatformInfoProvider
import com.muralex.data.common.RemoteDataSource
import com.russhwolf.settings.Settings
import kotlinx.coroutines.withContext


class Repository(
    val localDb: LocalDataSource,
    val webservices: RemoteDataSource,
    val settings: Settings = Settings(),
    val platformInfo: PlatformInfoProvider,
    val dispatchers: DispatchersProvider,
) {

    val localSettings by lazy { MySettings(settings) }
    val runtimeCache get() = CacheObjects

    suspend fun <T> withRepoContext (block: suspend () -> T) : T {
        return withContext(dispatchers.io) {
            block()
        }
    }
}



