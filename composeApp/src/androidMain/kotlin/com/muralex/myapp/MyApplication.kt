package com.muralex.myapp

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.di.dataModule
import com.example.di.localdbModule
import com.example.di.networkModule
import com.example.di.platformInfoModule
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
                platformInfoModule,
                dataModule
            )
        }

        model = DKMPViewModel.getAndroidInstance()

        val appLifecycleObserver = AppLifecycleObserver(model)
        ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleObserver)
    }
}

class AppLifecycleObserver (private val model: DKMPViewModel) : LifecycleEventObserver {
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START ->
                model.navigation.onEnterForeground()
            Lifecycle.Event.ON_STOP ->
                model.navigation.onEnterBackground()
            else ->
                return
        }
    }
}

