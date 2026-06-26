package com.exploramus.data.repository

import com.exploramus.core.common.DispatchersProvider
import com.exploramus.data.common.AssetsDataSource
import com.exploramus.data.common.LocalDataSource
import com.exploramus.data.common.PlatformInfoProvider
import com.exploramus.data.common.RemoteDataSource
import com.exploramus.data.repository.sources.localsettings.MySettings
import com.exploramus.data.repository.sources.runtimecache.RuntimeCache
import com.russhwolf.settings.Settings
import kotlinx.coroutines.withContext


class Repository(
    val localDb: LocalDataSource,
    val webservices: RemoteDataSource,
    val assetsDataSource: AssetsDataSource,
    val settings: Settings = Settings(),
    val platformInfo: PlatformInfoProvider,
    val dispatchers: DispatchersProvider,
) {

    val localSettings by lazy { MySettings(settings) }
    val runtimeCache = RuntimeCache()

    suspend fun <T> withRepoContext (block: suspend () -> T) : T {
        return withContext(dispatchers.io) {
            block()
        }
    }
}



