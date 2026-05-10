package com.example.di

import com.muralex.core.common.DispatchersProvider
import com.muralex.data.Repository
import com.muralex.data.common.RemoteDataSource
import com.muralex.network.ApiClient
import com.muralex.network.RemoteDataSourceImpl
import com.muralex.network.api.CountryApi
import com.muralex.network.environment.EnvironmentProvider
import org.koin.core.module.Module
import org.koin.dsl.module


expect val localdbModule: Module

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
            dispatchers = DispatchersProvider.Base()
        )
    }
}

fun appModules() = listOf(
    localdbModule,
    networkModule,
    dataModule
)