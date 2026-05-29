package com.muralex.exploramus

import com.muralex.data.repository.Repository
import com.muralex.exploramus.viewmodel.DKMPViewModel
import org.koin.mp.KoinPlatform

fun DKMPViewModel.Factory.getAndroidInstance(): DKMPViewModel {
    val repository = KoinPlatform.getKoin().get<Repository>()
    return DKMPViewModel(repository)
}