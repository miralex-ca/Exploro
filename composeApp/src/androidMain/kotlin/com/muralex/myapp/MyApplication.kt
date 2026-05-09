package com.muralex.myapp

import android.app.Application
import com.example.di.dataModule
import com.example.di.localdbModule
import com.example.di.networkModule
import com.muralex.myapp.viewmodel.DKMPViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    lateinit var model: DKMPViewModel

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                localdbModule,
                networkModule,
                dataModule
            )
        }

        model = DKMPViewModel.getAndroidInstance()
    }
}