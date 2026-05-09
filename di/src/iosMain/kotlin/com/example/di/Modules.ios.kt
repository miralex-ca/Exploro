package com.example.di

import com.muralex.data.common.LocalDataSource
import com.muralex.localdb.createIosLocalDataSource
import org.koin.dsl.module

actual val localdbModule = module {
    single<LocalDataSource> {
        createIosLocalDataSource()
    }
}