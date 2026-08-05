package com.exploramus.app.composables.screens.favorites.views

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
import com.exploramus.app.composables.LocalAppEnvironment
import com.exploramus.app.composables.components.ResourceImage
import com.exploramus.app.composables.components.flagAssetUri
import com.exploramus.app.composables.screens.favorites.FavoritesUiEvent
import com.exploramus.app.composables.screens.favorites.FavoritesUiEvent.OnItemClicked
import com.exploramus.app.composables.screens.favorites.FavoritesUiEvent.RemoveFavorite
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.adaptive.layout
import com.exploramus.app.design.adaptive.useBottomBar
import com.exploramus.app.design.adaptive.value
import com.exploramus.app.design.theme.appColors
import com.exploramus.shared.viewmodel.screens.favorites.FavoriteListItem
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun FavoritesList(
    listItems:  List<FavoriteListItem>,
    onEvent: (FavoritesUiEvent) -> Unit,
) {
    val isFavoriteSwipeEnabled = LocalAppEnvironment.current.favoriteSwipeEnabled

    val layout = MaterialTheme.layout.favorites
    val formFactor = LocalFormFactor.current
    val bottomPadding = layout.bottomPadding.value() +
            if (formFactor.useBottomBar) 60.dp else 0.dp

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            contentPadding = PaddingValues(
                start = 16.dp, end = 16.dp,
                top = 12.dp, bottom = bottomPadding
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = listItems,
                key = { it.id }
            ) { item ->
                SwipeableFavoriteRow(
                    item = item,
                    isSwipeEnabled = isFavoriteSwipeEnabled,
                    onClick = { onEvent(OnItemClicked(item)) },
                    onRemove = { onEvent(RemoveFavorite(item.id)) },
                    modifier = Modifier.widthIn(max = layout.listItemMaxWidth.value())
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
            delay(200.milliseconds)
            onRemove()
        }
    }

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = isSwipeEnabled,
        modifier = modifier,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .padding(2.dp)
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
                modifier = Modifier
            ) {
                FavoriteListRow(
                    modifier = Modifier.fillMaxWidth(),
                    item = item,
                    showDropdownMenu = !isSwipeEnabled,
                    onClick = onClick,
                    onRemove = onRemove
                )
            }
        }
    }
}

@Composable
fun FavoriteListRow(
    modifier: Modifier = Modifier,
    item: FavoriteListItem,
    showDropdownMenu: Boolean,
    onClick: () -> Unit,
    onRemove: () -> Unit,
) {
    val layout = MaterialTheme.layout.favorites

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.appColors.cardBorder
        ),
        onClick = onClick
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 10.dp, 28.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                ResourceImage(
                    imageUri = flagAssetUri(item.iso2),
                    modifier = Modifier
                        .height(layout.itemImageHeight.value())
                        .width(90.dp)
                        .border(
                            width = 1.dp,
                            color = Color.LightGray.copy(alpha = 0.45f),
                            shape = RoundedCornerShape(6.dp)
                        ),
                    shape = RoundedCornerShape(6.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(layout.imageTextSpace.value()))

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = item.location,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            if (showDropdownMenu) {
                FavoriteDropdownMenu(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 4.dp),
                    onClick = onClick,
                    onRemove = onRemove
                )
            }
        }
    }
}