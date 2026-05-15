package com.muralex.myapp.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.*
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.muralex.models.CountryListItem
import com.muralex.myapp.viewmodel.screens.favorites.FavoritesScreenState
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoritesScreen(
    screenState: FavoritesScreenState,
    onListItemClick: (CountryListItem) -> Unit,
    toggleFavorite: (String) -> Unit
) {
    if (screenState.isLoading) {
        Text(
            text = "Loading ...",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
    } else {
        if (screenState.favorites.isEmpty()) {
            Text(
                text = "empty list",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(
                        horizontal = 8.dp,
                        vertical = 12.dp
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
                            onClick = { onListItemClick(item) },
                            onRemove = { toggleFavorite(item.id) },
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun FavoriteListRow(
    item: CountryListItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(
            1.dp,
            Color.LightGray.copy(alpha = 0.25f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.flagPngUrl,
                contentDescription = null,
                modifier = Modifier
                    .height(52.dp)
                    .width(90.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .border(
                        width = 1.dp,
                        color = Color.LightGray.copy(alpha = 0.45f),
                        shape = RoundedCornerShape(6.dp)
                    ),

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
    item: CountryListItem,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        initialValue = SwipeToDismissBoxValue.Settled,
        positionalThreshold = { it * 0.70f },
    )
    var handled by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        if (handled) 0f else 1f
    )

    LaunchedEffect(dismissState.currentValue) {
        if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart && !handled) {
            handled = true
            delay(700)
            onRemove()
        }
    }

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = true,
        modifier = Modifier
            .padding(horizontal = 12.dp),
        backgroundContent = {
            Box(
                modifier = Modifier
                    .padding(vertical = 3.dp)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Color(0xFFF85146).copy(alpha = 0.9f * alpha)
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
            exit = fadeOut(animationSpec = tween(durationMillis = 100))
                    + shrinkVertically(
                animationSpec = tween(durationMillis = 200, delayMillis = 200)
            )
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