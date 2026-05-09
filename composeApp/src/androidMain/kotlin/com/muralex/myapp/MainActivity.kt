package com.muralex.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.di.dataModule
import com.example.di.localdbModule
import com.example.di.networkModule
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        val model = (application as MyApplication).model

        super.onCreate(savedInstanceState)

        setContent {
            App(model)
        }
    }
}

//@Preview
//@Composable
//fun AppAndroidPreview() {
//    App()
//}