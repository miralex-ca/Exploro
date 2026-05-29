package com.muralex.exploramus.screens.favorites

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.muralex.exploramus.LocalAppEnvironment
import com.muralex.exploramus.screens.favorites.FavoritesUiEvent.OnItemClicked
import com.muralex.exploramus.screens.favorites.FavoritesUiEvent.RemoveFavorite
import com.muralex.exploramus.ui.components.EmptyState
import com.muralex.exploramus.ui.components.EmptyStateView
import com.muralex.exploramus.ui.components.FadeLoadingContent
import com.muralex.exploramus.ui.components.RemoteImage
import com.muralex.exploramus.ui.theme.appColors
import com.muralex.exploramus.viewmodel.screens.favorites.FavoriteListItem
import com.muralex.exploramus.viewmodel.screens.favorites.FavoritesScreenState
import kotlinx.coroutines.delay


@Composable
fun FavoritesScreen(
    screenState: FavoritesScreenState,
    eventHandler: FavoritesEventHandler
) {
    FadeLoadingContent(isLoading = screenState.isLoading) {
        FavoritesScreenContent(
            screenState = screenState,
            onEvent = eventHandler::onEvent,
        )
    }
}

@Composable
fun FavoritesScreenContent(
    screenState: FavoritesScreenState,
    onEvent: (FavoritesUiEvent) -> Unit,
) {
    val isFavoriteSwipeEnabled = LocalAppEnvironment.current.favoriteSwipeEnabled

    if (screenState.favorites.isEmpty()) {
        EmptyStateView(EmptyState.EmptyList)
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                contentPadding = PaddingValues(
                    start = 8.dp, end = 8.dp,
                    top = 12.dp, bottom = 60.dp
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 600.dp)
            ) {
                items(
                    items = screenState.favorites,
                    key = { it.id }
                ) { item ->
                    SwipeableFavoriteRow(
                        item = item,
                        isSwipeEnabled = isFavoriteSwipeEnabled,
                        onClick = { onEvent(OnItemClicked(item)) },
                        onRemove = { onEvent(RemoveFavorite(item.id)) },
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteListRow(
    item: FavoriteListItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.appColors.cardBorder
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            RemoteImage(
                imageUrl = item.flagPngUrl,
                modifier = Modifier
                    .height(52.dp)
                    .width(90.dp)
                    .border(
                        width = 1.dp,
                        color = Color.LightGray.copy(alpha = 0.45f),
                        shape = RoundedCornerShape(6.dp)
                    ),
                shape = RoundedCornerShape(6.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(26.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = item.subregion,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Composable
fun SwipeableFavoriteRow(
    modifier: Modifier = Modifier,
    item: FavoriteListItem,
    isSwipeEnabled: Boolean = true,
    onClick: () -> Unit,
    onRemove: () -> Unit,
) {
    val dismissState = rememberSwipeToDismissBoxState(
        initialValue = SwipeToDismissBoxValue.Settled,
        positionalThreshold = { it * 0.60f }, // seems to be not working
    )
    var handled by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        if (handled) 0f else 1f
    )

    LaunchedEffect(dismissState.currentValue) {
        if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart && !handled) {
            handled = true
            delay(200)
            onRemove()
        }
    }

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = isSwipeEnabled,
        modifier = modifier
            .padding(horizontal = 12.dp),
        backgroundContent = {
            Box(
                modifier = Modifier
                    .padding(vertical = 3.dp)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        MaterialTheme.appColors.destructive.copy(alpha = 0.9f * alpha)
                    )
                    .alpha(alpha)
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    ) {

        AnimatedVisibility(
            visible = !handled,
            exit = fadeOut(animationSpec = tween(durationMillis = 150))
        ) {
            Box(
                modifier = Modifier.padding(vertical = 3.dp)
            ) {
                FavoriteListRow(
                    item = item,
                    onClick = onClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}