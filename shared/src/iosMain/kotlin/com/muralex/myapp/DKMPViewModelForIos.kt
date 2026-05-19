package com.muralex.myapp

import com.muralex.data.repository.Repository
import com.muralex.myapp.viewmodel.DKMPViewModel
import org.koin.mp.KoinPlatform

//import com.muralex.data.createIosRepository
//import com.muralex.myapp.viewmodel.DKMPViewModel
//
//fun DKMPViewModel.Factory.getIosInstance(): DKMPViewModel {
//    val repository = createIosRepository()
//    return DKMPViewModel(repository)
//}


fun DKMPViewModel.Factory.getIosInstance(): DKMPViewModel {
    val repository = KoinPlatform.getKoin().get<Repository>()
    return DKMPViewModel(repository)
}