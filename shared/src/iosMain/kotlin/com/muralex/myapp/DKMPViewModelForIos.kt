package com.muralex.myapp

import com.muralex.data.createIosRepository
import com.muralex.myapp.viewmodel.DKMPViewModel

fun DKMPViewModel.Factory.getIosInstance(): DKMPViewModel {
    val repository = createIosRepository()
    return DKMPViewModel(repository)
}