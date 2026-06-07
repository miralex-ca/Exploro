package com.muralex.exploramus

import com.example.di.dataModule
import com.example.di.localdbModule
import com.example.di.networkModule
import com.example.di.platformInfoModule
import com.muralex.data.repository.Repository
import com.muralex.exploramus.viewmodel.core.DKMPViewModel
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform

fun DKMPViewModel.Factory.getIosInstance(): DKMPViewModel {
    val repository = KoinPlatform.getKoin().get<Repository>()
    return DKMPViewModel(repository)
}

