package com.muralex.exploramus

import com.muralex.data.repository.Repository
import com.muralex.exploramus.viewmodel.DKMPViewModel
import org.koin.mp.KoinPlatform

//import android.content.Context
//import com.muralex.data.createAndroidRepository
//import com.muralex.exploramus.viewmodel.DKMPViewModel
//
//fun DKMPViewModel.Factory.getAndroidInstance(context : Context): DKMPViewModel {
//    val repository = createAndroidRepository(context)
//    return DKMPViewModel(repository)
//}

fun DKMPViewModel.Factory.getAndroidInstance(): DKMPViewModel {
    val repository = KoinPlatform.getKoin().get<Repository>()
    return DKMPViewModel(repository)
}