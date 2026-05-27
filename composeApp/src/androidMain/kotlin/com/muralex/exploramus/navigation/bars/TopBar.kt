package com.muralex.exploramus.navigation.bars

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.muralex.exploramus.ui.theme.appColors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Level1TopBar(
    title: String,
    onSettingsClick: () -> Unit,
    onSearchClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                modifier = Modifier.padding(start = 12.dp)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.appColors.topBarContainer,
            titleContentColor = MaterialTheme.appColors.onTopBarContainer,
            navigationIconContentColor = MaterialTheme.appColors.onTopBarContainer,
            actionIconContentColor = MaterialTheme.appColors.onTopBarContainer,
        ),
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                )
            }

            IconButton( onClick = onSettingsClick ) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "Settings"
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.appColors.topBarContainer,
            titleContentColor = MaterialTheme.appColors.onTopBarContainer,
            navigationIconContentColor = MaterialTheme.appColors.onTopBarContainer,
            actionIconContentColor = MaterialTheme.appColors.onTopBarContainer,
        ),
        navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Back",
                )
            }
        }
    )
}