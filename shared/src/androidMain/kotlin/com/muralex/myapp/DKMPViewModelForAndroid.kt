package com.muralex.myapp

import android.content.Context
import com.muralex.data.createAndroidRepository
import com.muralex.myapp.viewmodel.DKMPViewModel

fun DKMPViewModel.Factory.getAndroidInstance(context : Context): DKMPViewModel {
    val repository = createAndroidRepository(context)
    return DKMPViewModel(repository)
}