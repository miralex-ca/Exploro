package com.example.di

import com.exploramus.assets.JsonAssetsDataSource
import com.exploramus.assets.createAndroidAssetFileReader
import com.muralex.data.common.AssetFileReader
import com.muralex.data.common.AssetsDataSource
import com.muralex.data.common.LocalDataSource
import com.muralex.data.common.PlatformInfoProvider
import com.muralex.data.repository.createAndroidPlatformInfoProvider
import com.muralex.localdb.createAndroidLocalDataSource
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