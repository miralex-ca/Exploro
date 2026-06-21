package com.exploramus.shared

import com.exploramus.di.appModules
import com.exploramus.di.dataModule
import com.exploramus.di.localdbModule
import com.exploramus.di.networkModule
import com.exploramus.di.platformInfoModule
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