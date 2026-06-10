package com.muralex.exploramus.screens.favorites.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.muralex.exploramus.screens.favorites.FavoritesUiEvent
import com.muralex.exploramus.screens.favorites.FavoritesUiEvent.OnItemClicked
import com.muralex.exploramus.screens.favorites.FavoritesUiEvent.RemoveFavorite
import com.muralex.exploramus.ui.adaptive.LocalFormFactor
import com.muralex.exploramus.ui.adaptive.layout
import com.muralex.exploramus.ui.adaptive.useBottomBar
import com.muralex.exploramus.ui.adaptive.value
import com.muralex.exploramus.ui.components.RemoteImage
import com.muralex.exploramus.ui.theme.appColors
import com.muralex.exploramus.viewmodel.screens.favorites.FavoriteListItem


@Composable
fun FavoritesGrid(
    gridItems: List<FavoriteListItem>,
    onEvent: (FavoritesUiEvent) -> Unit,
) {

    val formFactor = LocalFormFactor.current
    val layout = MaterialTheme.layout.favorites
    val bottomPadding = layout.bottomPadding.value() + if (formFactor.useBottomBar) 60.dp else 0.dp

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        val horizontalPadding = ((maxWidth - 740.dp) / 2)
            .coerceIn(36.dp, 150.dp)

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 280.dp),
            contentPadding = PaddingValues(
                start = horizontalPadding, end = horizontalPadding,
                top = 12.dp, bottom = bottomPadding
            ),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(
                items = gridItems,
                key = { it.id }
            ) { item ->
                FavoriteGridCell(
                    item = item,
                    onClick = { onEvent(OnItemClicked(item)) },
                    onRemove = { onEvent(RemoveFavorite(item.id)) },
                )
            }
        }
    }
}


@Composable
fun FavoriteGridCell(
    modifier: Modifier = Modifier,
    item: FavoriteListItem,
    onClick: () -> Unit,
    onRemove: () -> Unit,
) {

    val layout = MaterialTheme.layout.favorites

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

        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 10.dp, 24.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                RemoteImage(
                    imageUrl = item.flagPngUrl,
                    modifier = Modifier
                        .height(layout.itemGridImageHeight.value())
                        .width(120.dp)
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
                        text = item.subregion,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            FavoriteDropdownMenu(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp),
                onClick = onClick,
                onRemove = onRemove
            )
        }

    }
}
