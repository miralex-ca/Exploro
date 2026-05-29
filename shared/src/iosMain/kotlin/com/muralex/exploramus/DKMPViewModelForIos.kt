package com.muralex.exploramus

import com.muralex.data.repository.Repository
import com.muralex.exploramus.viewmodel.core.DKMPViewModel
import org.koin.mp.KoinPlatform

fun DKMPViewModel.Factory.getIosInstance(): DKMPViewModel {
    val repository = KoinPlatform.getKoin().get<Repository>()
    return DKMPViewModel(repository)
}