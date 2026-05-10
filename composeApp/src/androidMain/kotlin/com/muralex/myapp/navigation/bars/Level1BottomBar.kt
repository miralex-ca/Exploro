package com.muralex.myapp.navigation.bars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.muralex.myapp.viewmodel.Navigation
import com.muralex.myapp.viewmodel.ScreenIdentifier
import com.muralex.myapp.viewmodel.screens.Level1Navigation

@Composable
fun Navigation.Level1BottomBar(
    selectedTab: ScreenIdentifier,
    navigateByLevel1Menu: (Level1Navigation) -> Unit
) {
    NavigationBar(content = {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Menu, "ALL") },
            label = { Text("All Countries", fontSize = 13.sp) },
            selected = selectedTab.URI == Level1Navigation.AllCountries.screenIdentifier.URI,
            onClick = { navigateByLevel1Menu(Level1Navigation.AllCountries) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Star, "FAVORITES") },
            label = { Text("Favourites", fontSize = 13.sp) },
            selected = selectedTab.URI == Level1Navigation.FavoriteCountries.screenIdentifier.URI,
            onClick = { navigateByLevel1Menu(Level1Navigation.FavoriteCountries) }
        )
    })
}