package com.muralex.data

import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import appLocal.AppDatabase
import appLocal.Countries
import com.muralex.data.sources.localsettings.MySettings
import com.muralex.data.sources.runtimecache.CacheObjects
import com.muralex.data.sources.webservices.ApiClient
import com.russhwolf.settings.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//
class Repository(val sqlDriver: SqlDriver, val settings: Settings = Settings(), val runSynchronously: Boolean = false) {

    internal val webservices by lazy { ApiClient() }

    internal val localDb by lazy {
        AppDatabase(
            sqlDriver,
            Countries.Adapter(IntColumnAdapter)
        )
    }

    internal val localSettings by lazy { MySettings(settings) }
    internal val runtimeCache get() = CacheObjects

    // we run each repository function on a Dispatchers.Default coroutine
    // we pass runSynchronously=true just for the TestRepository instance
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

val debugLogger = RepoDebugLogger()

class RepoDebugLogger () {
    fun log(message: String) {
        println(message)
    }
}
