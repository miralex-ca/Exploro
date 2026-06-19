package com.muralex.exploramus.composables.navigation.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.muralex.exploramus.viewmodel.core.ScreenIdentifier


@Composable
fun RowScope.BottomBarItem(item: NavItemData, selectedTab: ScreenIdentifier) {
    val isSelected = item.isSelected(selectedTab)
    NavigationBarItem(
        icon = { Icon(if (isSelected) item.selectedIcon else item.icon, contentDescription = null) },
        label = { Text(item.label(), fontSize = 13.sp) },
        selected = isSelected,
        onClick = item.onClick
    )
}

@Composable
fun RailItem(item: NavItemData, selectedTab: ScreenIdentifier) {
    val isSelected = item.isSelected(selectedTab)
    NavigationRailItem(
        icon = { Icon(if (isSelected) item.selectedIcon else item.icon, contentDescription = null) },
        label = { Text(item.label(), fontSize = 12.sp) },
        selected = isSelected,
        onClick = item.onClick
    )
}


@Composable
fun DrawerItem(item: NavItemData, selectedTab: ScreenIdentifier) {
    val isSelected = item.isSelected(selectedTab)
    NavigationDrawerItem(
        icon = { Icon(if (isSelected) item.selectedIcon else item.icon, contentDescription = null) },
        label = { Text(item.label()) },
        selected = isSelected,
        onClick = item.onClick,
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}