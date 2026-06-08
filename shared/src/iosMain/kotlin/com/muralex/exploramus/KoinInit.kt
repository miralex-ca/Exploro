package com.muralex.exploramus

import com.example.di.appModules
import com.example.di.dataModule
import com.example.di.localdbModule
import com.example.di.networkModule
import com.example.di.platformInfoModule
import org.koin.core.context.startKoin

//fun initKoin() {
//    startKoin {
//        modules(
//            localdbModule,
//            networkModule,
//            platformInfoModule,
//            dataModule
//        )
//    }
//}

fun initKoin() {
    startKoin {
        modules(appModules())
    }
}