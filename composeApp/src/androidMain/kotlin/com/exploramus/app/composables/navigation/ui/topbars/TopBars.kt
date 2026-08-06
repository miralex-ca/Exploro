package com.exploramus.app.composables.navigation.ui.topbars

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.exploramus.app.R
import com.exploramus.app.design.adaptive.FormFactor
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.adaptive.isCompactHeight
import com.exploramus.app.design.theme.appColors
import com.exploramus.app.resources.Strings


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Level1TopBar(
    title: String,
    hasActions: Boolean,
    onSettingsClick: () -> Unit,
    onSearchClick: () -> Unit,
) {
    val formFactor = LocalFormFactor.current

    TopAppBar(
        title = {
            Text(
                text = title,
                modifier = Modifier.padding(start = 12.dp)
            )
        },
        expandedHeight = topBarAdaptiveHeight(formFactor),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.appColors.topBarContainer,
            titleContentColor = MaterialTheme.appColors.onTopBarContainer,
            navigationIconContentColor = MaterialTheme.appColors.onTopBarContainer,
            actionIconContentColor = MaterialTheme.appColors.onTopBarContainer,
        ),
        actions = {
            if (hasActions) {
                IconButton(onClick = onSearchClick) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = Strings.commonSearch,
                    )
                }

                IconButton( onClick = onSettingsClick ) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = Strings.commonSettings
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onBackClick: () -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    val formFactor = LocalFormFactor.current

    TopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        expandedHeight = topBarAdaptiveHeight(formFactor),
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
                    contentDescription = Strings.commonBack,
                )
            }
        },
        actions = actions
    )
}

@Composable
fun topBarAdaptiveHeight(formFactor: FormFactor): Dp =
    if (formFactor.isCompactHeight) {
        46.dp
    } else {
        TopAppBarDefaults.TopAppBarExpandedHeight
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar(
    title: String,
    mapsUrl: String? = null,
    wikiUrl: String? = null,
    onBackClick: () -> Unit
) {
    val formFactor = LocalFormFactor.current
    val uriHandler = LocalUriHandler.current
    var menuExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.testTag("details_title")
            )
        },
        expandedHeight = topBarAdaptiveHeight(formFactor),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.appColors.topBarContainer,
            titleContentColor = MaterialTheme.appColors.onTopBarContainer,
            navigationIconContentColor = MaterialTheme.appColors.onTopBarContainer,
            actionIconContentColor = MaterialTheme.appColors.onTopBarContainer,
        ),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = Strings.commonBack,
                )
            }
        },
        actions = {
            if (!mapsUrl.isNullOrBlank() || !wikiUrl.isNullOrBlank()) {
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = Strings.commonMore,
                    )
                }
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    if (!mapsUrl.isNullOrBlank()) {
                        DropdownMenuItem(
                            text = { Text(Strings.commonOpenInMaps) },
                            leadingIcon = {
                                Icon(imageVector = Icons.Outlined.Place, contentDescription = null)
                            },
                            onClick = {
                                menuExpanded = false
                                uriHandler.openUri(mapsUrl)
                            }
                        )
                    }
                    if (!wikiUrl.isNullOrBlank()) {
                        DropdownMenuItem(
                            text = { Text(Strings.commonOpenInWikipedia) },
                            leadingIcon = {
                                Image(
                                    painter = painterResource(R.drawable.wikipedia),
                                    contentDescription = Strings.commonOpenInWikipedia,
                                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                                )
                            },
                            onClick = {
                                menuExpanded = false
                                uriHandler.openUri(wikiUrl)
                            }
                        )
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsPagerTopBar(
    title: String,
    mapsUrl: String? = null,
    wikiUrl: String? = null,
    onBackClick: () -> Unit,
    onPreviousClick: (() -> Unit)? = null,
    onNextClick: (() -> Unit)? = null,
    hasPrevious: Boolean = false,
    hasNext: Boolean = false,
) {
    val formFactor = LocalFormFactor.current
    val uriHandler = LocalUriHandler.current
    var menuExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.testTag("details_title")
            )
        },
        expandedHeight = topBarAdaptiveHeight(formFactor),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.appColors.topBarContainer,
            titleContentColor = MaterialTheme.appColors.onTopBarContainer,
            navigationIconContentColor = MaterialTheme.appColors.onTopBarContainer,
            actionIconContentColor = MaterialTheme.appColors.onTopBarContainer,
        ),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = Strings.commonBack,
                )
            }
        },
        actions = {
            if (onPreviousClick != null || onNextClick != null) {
                IconButton(
                    onClick = { onPreviousClick?.invoke() },
                    enabled = hasPrevious,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                        contentDescription = Strings.commonPrevious,
                    )
                }
                IconButton(
                    onClick = { onNextClick?.invoke() },
                    enabled = hasNext,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                        contentDescription = Strings.commonNext,
                    )
                }
            }

            if (!mapsUrl.isNullOrBlank() || !wikiUrl.isNullOrBlank()) {
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = Strings.commonMore,
                    )
                }
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    if (!mapsUrl.isNullOrBlank()) {
                        DropdownMenuItem(
                            text = { Text(Strings.commonOpenInMaps) },
                            leadingIcon = {
                                Icon(imageVector = Icons.Outlined.Place, contentDescription = null)
                            },
                            onClick = {
                                menuExpanded = false
                                uriHandler.openUri(mapsUrl)
                            }
                        )
                    }
                    if (!wikiUrl.isNullOrBlank()) {
                        DropdownMenuItem(
                            text = { Text(Strings.commonOpenInWikipedia) },
                            leadingIcon = {
                                Image(
                                    painter = painterResource(R.drawable.wikipedia),
                                    contentDescription = Strings.commonOpenInWikipedia,
                                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                                )
                            },
                            onClick = {
                                menuExpanded = false
                                uriHandler.openUri(wikiUrl)
                            }
                        )
                    }
                }
            }
        }
    )
}