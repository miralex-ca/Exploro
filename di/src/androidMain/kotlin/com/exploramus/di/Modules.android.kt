package com.exploramus.di

import com.exploramus.data.assets.JsonAssetsDataSource
import com.exploramus.data.assets.createAndroidAssetFileReader
import com.exploramus.data.common.AssetFileReader
import com.exploramus.data.common.AssetsDataSource
import com.exploramus.data.common.LocalDataSource
import com.exploramus.data.common.PlatformInfoProvider
import com.exploramus.data.localdb.createAndroidLocalDataSource
import com.exploramus.data.repository.createAndroidPlatformInfoProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val localdbModule = module {
    single<LocalDataSource> {
        createAndroidLocalDataSource(androidContext())
    }
}

actual val platformInfoModule = module {
    single<PlatformInfoProvider> {
        createAndroidPlatformInfoProvider(androidContext())
    }
}

actual val assetsModule = module {
    single<AssetFileReader> { createAndroidAssetFileReader(androidContext()) }
    single<AssetsDataSource> { JsonAssetsDataSource(get()) }
}