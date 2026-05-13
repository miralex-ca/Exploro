package com.muralex.myapp.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.ui.Modifier
import com.muralex.myapp.navigation.bars.Level1BottomBar
import com.muralex.myapp.navigation.bars.TopBar
import com.muralex.myapp.viewmodel.Navigation
import com.muralex.myapp.viewmodel.NavigationState

@Composable
fun Navigation.OnePane(
    saveableStateHolder: SaveableStateHolder,
    localNavigationState: MutableState<NavigationState>
) {
    val screenIdentifier = localNavigationState.value.topScreenIdentifier
    val title = getTitle(screenIdentifier)
    Scaffold(
        topBar = { TopBar(title) },
        content = { contentPadding ->
            Row(Modifier.padding(contentPadding)) {
                saveableStateHolder.SaveableStateProvider(screenIdentifier.URI) {
                    ScreenPicker(
                        screenIdentifier = screenIdentifier,
                        navigate = navigationProcessor(localNavigationState),
                        navigateByLevel1 = level1NavigationProcessor(localNavigationState)
                    )
                }
            }
        },
        bottomBar = {
            if (screenIdentifier.screen.navigationLevel == 1)
                Level1BottomBar(
                    selectedTab = screenIdentifier,
                    navigateByLevel1Menu = level1NavigationProcessor(localNavigationState)
                )
        }
    )
}