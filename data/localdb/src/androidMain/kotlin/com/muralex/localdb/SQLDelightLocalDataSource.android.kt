package com.muralex.localdb

import com.muralex.data.common.LocalDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

//actual val localdbModule = module {
//    single<LocalDataSource> {
//        SQLDelightLocalDataSource(createAndroidSqliteDriver(androidContext()))
//    }
//}