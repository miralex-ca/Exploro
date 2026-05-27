package com.muralex.myapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            window.isNavigationBarContrastEnforced = false
//        }

        val model = (application as MyApplication).model

        super.onCreate(savedInstanceState)

        setContent {
            MainComposable(model.navigation)
        }
    }
}