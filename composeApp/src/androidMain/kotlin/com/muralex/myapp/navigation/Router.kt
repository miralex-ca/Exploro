package com.muralex.myapp.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.muralex.myapp.viewmodel.LaunchScreenState
import com.muralex.myapp.viewmodel.Navigation
import com.muralex.myapp.viewmodel.NavigationState
import com.muralex.myapp.viewmodel.ScreenIdentifier
import com.muralex.myapp.viewmodel.ScreenParams
import com.muralex.myapp.viewmodel.screens.Level1Navigation
import com.muralex.myapp.viewmodel.screens.Screen

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun Navigation.Router() {
    val screenUIsStateHolder = rememberSaveableStateHolder()
    val localNavigationState = remember { mutableStateOf( navigationState ) }
    val launchScreenState by stateProvider.getLaunchScreenStateFlow().collectAsState()

    if (launchScreenState == LaunchScreenState.ACTIVE) {
        Scaffold { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Loading ...")
            }
        }
    } else {
        BoxWithConstraints {
            OnePane(screenUIsStateHolder, localNavigationState)
        }

        HandleBackButton(screenUIsStateHolder, localNavigationState)
    }


}

