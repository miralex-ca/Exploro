package com.muralex.myapp.datalayer

import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import com.muralex.myapp.datalayer.sources.localdb.Countries
import com.muralex.myapp.datalayer.sources.localsettings.MySettings
import com.muralex.myapp.datalayer.sources.runtimecache.CacheObjects
import com.muralex.myapp.datalayer.sources.webservices.ApiClient
import com.russhwolf.settings.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mylocal.db.LocalDb
import kotlin.getValue

class Repository(val sqlDriver: SqlDriver, val settings: Settings = Settings(), val runSynchronously: Boolean = false) {

    internal val webservices by lazy { ApiClient() }
    internal val localDb by lazy {
        LocalDb(
            sqlDriver,
            Countries.Adapter(IntColumnAdapter, IntColumnAdapter, IntColumnAdapter)
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