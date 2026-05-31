package com.muralex.exploramus.navigation.bars

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muralex.exploramus.resources.Strings
import com.muralex.exploramus.viewmodel.core.Navigation
import com.muralex.exploramus.viewmodel.core.ScreenIdentifier
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

@Composable
fun Navigation.Level1NavRail(
    selectedTab: ScreenIdentifier,
    navigateByLevel1Menu: (Level1Navigation) -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
) {
    NavigationRail(
        containerColor = MaterialTheme.colorScheme.surface,
    ) {

        Spacer(modifier = Modifier.weight(1f))


        Column (
            modifier = Modifier.padding(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        )
        {

            NavigationRailItem(
                icon = { Icon(Icons.Default.Explore, contentDescription = null) },
                label = { Text(Strings.navBrowse, fontSize = 12.sp) },
                selected = selectedTab.URI == Level1Navigation.Home.screenIdentifier.URI,
                onClick = { navigateByLevel1Menu(Level1Navigation.Home) }
            )


            NavigationRailItem(
                icon = { Icon(Icons.Default.Star, contentDescription = null) },
                label = { Text(Strings.navFavorites, fontSize = 12.sp) },
                selected = selectedTab.URI == Level1Navigation.Favorites.screenIdentifier.URI,
                onClick = { navigateByLevel1Menu(Level1Navigation.Favorites) }
            )

            NavigationRailItem(
                icon = { Icon(Icons.Default.Search, contentDescription = null) },
                label = { Text(Strings.searchTitle, fontSize = 13.sp) },
                selected = false,
                onClick = onSearchClick
            )

            NavigationRailItem(
                icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                label = { Text(Strings.settingsTitle, fontSize = 13.sp) },
                selected = false,
                onClick = onSettingsClick
            )
        }


        Spacer(modifier = Modifier.weight(1f))

    }
}

@Composable
fun Navigation.Level1NavDrawer(
    selectedTab: ScreenIdentifier,
    navigateByLevel1Menu: (Level1Navigation) -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    content: @Composable () -> Unit
) {
    PermanentNavigationDrawer(
        drawerContent = {
            PermanentDrawerSheet(Modifier.width(240.dp)) {
                Column(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(horizontal = 12.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    Spacer(modifier = Modifier.height(60.dp))

                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Explore, contentDescription = null) },
                        label = { Text(Strings.navBrowse) },
                        selected = selectedTab.URI == Level1Navigation.Home.screenIdentifier.URI,
                        onClick = { navigateByLevel1Menu(Level1Navigation.Home) },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Star, contentDescription = null) },
                        label = { Text(Strings.navFavorites) },
                        selected = selectedTab.URI == Level1Navigation.Favorites.screenIdentifier.URI,
                        onClick = { navigateByLevel1Menu(Level1Navigation.Favorites) },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Search, contentDescription = null) },
                        label = { Text(Strings.searchTitle) },
                        selected = false,
                        onClick = onSearchClick,
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )

                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                        label = { Text(Strings.settingsTitle) },
                        selected = false,
                        onClick = onSettingsClick,
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )

                }
            }
        },
        content = content
    )
}
