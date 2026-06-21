package com.exploramus.shared

import com.exploramus.data.repository.Repository
import com.exploramus.shared.viewmodel.core.DKMPViewModel
import org.koin.mp.KoinPlatform

fun DKMPViewModel.Factory.getAndroidInstance(): DKMPViewModel {
    val repository = KoinPlatform.getKoin().get<Repository>()
    return DKMPViewModel(repository)
}