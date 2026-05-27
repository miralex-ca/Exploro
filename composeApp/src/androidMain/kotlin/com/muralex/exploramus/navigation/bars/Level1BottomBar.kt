package com.muralex.exploramus.navigation.bars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.muralex.exploramus.resources.Strings
import com.muralex.exploramus.viewmodel.Navigation
import com.muralex.exploramus.viewmodel.ScreenIdentifier
import com.muralex.exploramus.viewmodel.screens.Level1Navigation

@Composable
fun Navigation.Level1BottomBar(
    selectedTab: ScreenIdentifier,
    navigateByLevel1Menu: (Level1Navigation) -> Unit,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        content = {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Explore, "ALL") },
            label = { Text(Strings.navBrowse, fontSize = 13.sp) },
            selected = selectedTab.URI == Level1Navigation.Home.screenIdentifier.URI,
            onClick = { navigateByLevel1Menu(Level1Navigation.Home) }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Star, "FAVORITES") },
            label = { Text(Strings.navFavorites, fontSize = 13.sp) },
            selected = selectedTab.URI == Level1Navigation.Favorites.screenIdentifier.URI,
            onClick = { navigateByLevel1Menu(Level1Navigation.Favorites) }
        )
    })
}