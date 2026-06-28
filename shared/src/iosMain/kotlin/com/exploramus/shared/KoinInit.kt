package com.exploramus.shared

import com.exploramus.di.appModules
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