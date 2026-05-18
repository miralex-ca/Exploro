package com.muralex.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        val model = (application as MyApplication).model

        super.onCreate(savedInstanceState)

        setContent {
            MainComposable(model.navigation)
        }
    }
}