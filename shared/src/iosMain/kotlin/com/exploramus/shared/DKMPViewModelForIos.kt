package com.exploramus.shared

import com.exploramus.di.dataModule
import com.exploramus.di.localdbModule
import com.exploramus.di.networkModule
import com.exploramus.di.platformInfoModule
import com.exploramus.data.repository.Repository
import com.exploramus.shared.viewmodel.core.DKMPViewModel
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform

fun DKMPViewModel.Factory.getIosInstance(): DKMPViewModel {
    val repository = KoinPlatform.getKoin().get<Repository>()
    return DKMPViewModel(repository)
}

