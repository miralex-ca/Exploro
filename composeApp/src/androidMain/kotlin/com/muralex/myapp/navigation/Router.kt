package com.muralex.myapp.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muralex.myapp.viewmodel.AppStartupState
import com.muralex.myapp.viewmodel.Navigation
import com.muralex.myapp.viewmodel.screens.home.retryBootstrapApp

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun Navigation.Router() {
    val screenUIsStateHolder = rememberSaveableStateHolder()
    val localNavigationState = remember { mutableStateOf( navigationState ) }
    val startupState by stateProvider.getAppStartupStateFlow().collectAsStateWithLifecycle()

    when (startupState) {
        AppStartupState.Loading -> {
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
        }

        is AppStartupState.Error -> {

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    modifier = Modifier,
                    content = { Text("Retry") },
                    onClick = {
                        events.retryBootstrapApp()
                    }
                )
            }
        }

        AppStartupState.Ready -> {
            BoxWithConstraints {
                OnePane(screenUIsStateHolder, localNavigationState)
            }
            HandleBackButton(screenUIsStateHolder, localNavigationState)
        }


        else -> {}
    }

}

