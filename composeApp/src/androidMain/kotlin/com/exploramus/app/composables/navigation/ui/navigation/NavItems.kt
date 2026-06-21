package com.exploramus.app.composables.navigation.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.exploramus.app.resources.Strings
import com.exploramus.shared.viewmodel.core.ScreenIdentifier
import com.exploramus.shared.viewmodel.screens.Level1Navigation

class NavItems(
    val home: NavItemData,
    val favorites: NavItemData,
    val search: NavItemData,
    val settings: NavItemData,
) {
    val level1Items get() = listOf(home, favorites)
    val utilities get() = listOf(settings, search)
    val allItems get() = listOf(home, favorites, settings, search,)
}

@Composable
fun rememberNavItems(
    selectedTab: ScreenIdentifier,
    navigateByLevel1Menu: (Level1Navigation) -> Unit,
    onSearchClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
): NavItems = remember(selectedTab) {
    NavItems(
        home = NavItemData(
            icon = Icons.Outlined.Explore,
            selectedIcon = Icons.Filled.Explore,
            label = { Strings.navBrowse },
            navigation = Level1Navigation.Home,
            onClick = { navigateByLevel1Menu(Level1Navigation.Home) }
        ),
        favorites = NavItemData(
            icon = Icons.Default.StarOutline,
            selectedIcon = Icons.Default.Star,
            label = { Strings.navFavorites },
            navigation = Level1Navigation.Favorites,
            onClick = { navigateByLevel1Menu(Level1Navigation.Favorites) }
        ),
        settings = NavItemData(
            icon = Icons.Outlined.Settings,
            selectedIcon = Icons.Filled.Settings,
            label = { Strings.settingsTitle },
            navigation = Level1Navigation.Lv1Settings,
            onClick = onSettingsClick
        ),
        search = NavItemData(
            icon = Icons.Default.Search,
            label = { Strings.searchTitle },
            navigation = null,
            onClick = onSearchClick
        ),

    )
}
