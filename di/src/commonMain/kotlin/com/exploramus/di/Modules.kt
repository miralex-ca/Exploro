package com.exploramus.di

import com.exploramus.core.common.DispatchersProvider
import com.exploramus.data.repository.Repository
import com.exploramus.data.common.RemoteDataSource
import com.exploramus.data.network.ApiClient
import com.exploramus.data.network.RemoteDataSourceImpl
import com.exploramus.data.network.api.CountryApi
import com.exploramus.data.network.environment.EnvironmentProvider
import org.koin.core.module.Module
import org.koin.dsl.module


expect val localdbModule: Module
expect val platformInfoModule: Module
expect val assetsModule: Module

val networkModule = module {
    single { ApiClient() }
    single { EnvironmentProvider() }
    single {
        CountryApi(
            client = get(),
            environments = get ()
        )
    }

    single<RemoteDataSource> {
        RemoteDataSourceImpl(get())
    }
}

val dataModule = module {
    single {
        Repository(
            localDb = get(),
            webservices = get(),
            assetsDataSource = get(),
            platformInfo = get(),
            dispatchers = DispatchersProvider.Base()
        )
    }
}

fun appModules() = listOf(
    localdbModule,
    networkModule,
    assetsModule,
    platformInfoModule,
    dataModule,
)