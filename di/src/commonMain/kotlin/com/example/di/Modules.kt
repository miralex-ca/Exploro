package com.example.di

import com.muralex.core.common.DispatchersProvider
import com.muralex.data.Repository
import com.muralex.data.common.RemoteDataSource
import com.muralex.network.RemoteDataSourceImpl
import org.koin.core.module.Module
import org.koin.dsl.module


expect val localdbModule: Module

val networkModule = module {
    single<RemoteDataSource> { RemoteDataSourceImpl() }
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