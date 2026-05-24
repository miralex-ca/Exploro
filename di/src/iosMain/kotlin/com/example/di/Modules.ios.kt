package com.example.di

import com.muralex.data.common.LocalDataSource
import com.muralex.data.common.PlatformInfoProvider
import com.muralex.data.repository.createAndroidPlatformInfoProvider
import com.muralex.localdb.createIosLocalDataSource
import org.koin.dsl.module

actual val localdbModule = module {
    single<LocalDataSource> {
        createIosLocalDataSource()
    }
}

actual val platformInfoModule = module {
    single<PlatformInfoProvider> {
        createAndroidPlatformInfoProvider()
    }
}