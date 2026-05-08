package com.muralex.data

import com.muralex.data.common.LocalDataSource
import com.muralex.data.common.RemoteDataSource
import com.muralex.data.sources.localsettings.MySettings
import com.muralex.data.sources.runtimecache.CacheObjects
import com.muralex.network.RemoteDataSourceImpl
import com.russhwolf.settings.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    val localDb: LocalDataSource,
    val webservices: RemoteDataSource = RemoteDataSourceImpl(),
    val settings: Settings = Settings(),
    val runSynchronously: Boolean = false
) {

    val localSettings by lazy { MySettings(settings) }
    val runtimeCache get() = CacheObjects

    suspend fun <T> withRepoContext (block: suspend () -> T) : T {
        return if (runSynchronously) { // for testing
            block()
        } else { // for production
            withContext(Dispatchers.Default) {
                block()
            }
        }
    }
}


class RepoDebugLogger () {
    fun log(message: String) {
        println(message)
    }
}
