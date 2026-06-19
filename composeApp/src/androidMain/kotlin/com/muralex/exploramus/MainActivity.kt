package com.muralex.exploramus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.muralex.exploramus.composables.MainComposable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()

        val model = (application as App).model

        super.onCreate(savedInstanceState)

        setContent {
            MainComposable(model.navigation)
        }
    }
}