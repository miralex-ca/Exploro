package com.exploramus.app.composables.navigation.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.adaptive.isCompactHeight
import com.exploramus.shared.viewmodel.core.Navigation
import com.exploramus.shared.viewmodel.core.ScreenIdentifier
import com.exploramus.shared.viewmodel.screens.Level1Navigation

@Composable
fun Navigation.Level1BottomBar(
    selectedTab: ScreenIdentifier,
    navigateByLevel1Menu: (Level1Navigation) -> Unit,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        content = {
            val navItems = rememberNavItems(selectedTab, navigateByLevel1Menu)
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                navItems.level1Items.forEach { item ->
                    BottomBarItem(item, selectedTab)
                }
            }
        })
}

@Composable
fun Navigation.Level1NavRail(
    modifier: Modifier = Modifier,
    selectedTab: ScreenIdentifier,
    navigateByLevel1Menu: (Level1Navigation) -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
) {
    val formFactor = LocalFormFactor.current
    val isCompactHeight = formFactor.isCompactHeight

    NavigationRail(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        if (isCompactHeight) {
            Spacer(modifier = Modifier.weight(1f))
        } else {
            Spacer(modifier = Modifier.height(60.dp))
        }

        Column(
            modifier = Modifier.padding(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(if (isCompactHeight) 8.dp else 20.dp)
        ) {
            val navItems =
                rememberNavItems(selectedTab, navigateByLevel1Menu, onSearchClick, onSettingsClick)

            navItems.allItems.forEach { item ->
                RailItem(item, selectedTab)
            }
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

                    val navItems = rememberNavItems(
                        selectedTab,
                        navigateByLevel1Menu,
                        onSearchClick,
                        onSettingsClick
                    )

                    Spacer(modifier = Modifier.height(60.dp))

                    navItems.level1Items.forEach { item ->
                        DrawerItem(item, selectedTab)
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    navItems.utilities.forEach { item ->
                        DrawerItem(item, selectedTab)
                    }

                }
            }
        },
        content = content
    )
}
