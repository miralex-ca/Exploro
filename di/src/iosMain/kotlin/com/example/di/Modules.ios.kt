package com.example.di

import com.exploramus.assets.JsonAssetsDataSource
import com.exploramus.assets.createIOSAssetFileReader
import com.muralex.data.common.AssetFileReader
import com.muralex.data.common.AssetsDataSource
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

actual val assetsModule = module {
    single<AssetFileReader> { createIOSAssetFileReader() }
    single<AssetsDataSource> { JsonAssetsDataSource(get()) }
}