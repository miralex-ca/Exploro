package com.exploramus.di

import com.exploramus.data.assets.JsonAssetsDataSource
import com.exploramus.data.assets.createIOSAssetFileReader
import com.exploramus.data.common.AssetFileReader
import com.exploramus.data.common.AssetsDataSource
import com.exploramus.data.common.LocalDataSource
import com.exploramus.data.common.PlatformInfoProvider
import com.exploramus.data.repository.createAndroidPlatformInfoProvider
import com.exploramus.data.localdb.createIosLocalDataSource
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