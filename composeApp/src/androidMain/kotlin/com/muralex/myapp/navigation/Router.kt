package com.muralex.myapp.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.unit.dp
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


    BoxWithConstraints {
        OnePane(screenUIsStateHolder, localNavigationState)
    }

    HandleBackButton(screenUIsStateHolder, localNavigationState)

}

fun Navigation.navigationProcessor(localNavigationState: MutableState<NavigationState>) : (Screen, ScreenParams?) -> Unit {
    return { screen, screenParams ->
        val screenIdentifier = ScreenIdentifier.get(screen, screenParams)
        navigateToScreen(screenIdentifier) // shared navigationState is updated
        localNavigationState.value = navigationState // update localNavigationState
    }
}

fun Navigation.level1NavigationProcessor(localNavigationState: MutableState<NavigationState>) : (Level1Navigation) -> Unit {
    return {
        selectLevel1Navigation(it.screenIdentifier) // shared navigationState is updated
        localNavigationState.value = navigationState // update localNavigationState
    }
}