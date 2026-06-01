package com.muralex.exploramus.navigation.bars.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.muralex.exploramus.viewmodel.core.ScreenIdentifier
import com.muralex.exploramus.viewmodel.screens.Level1Navigation


data class NavItemData(
    val icon: ImageVector,
    val selectedIcon: ImageVector = icon,
    val label: @Composable () -> String,
    val navigation: Level1Navigation?,
    val onClick: () -> Unit,
)


fun NavItemData.isSelected(selectedTab: ScreenIdentifier): Boolean =
    navigation?.screenIdentifier?.URI == selectedTab.URI

