package com.exploramus.app.composables.screens.quizzes.groupeditems.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.exploramus.app.composables.components.RemoteImage
import com.exploramus.app.composables.screens.quizzes.groupeditems.GroupedItemsUiEvent
import com.exploramus.app.composables.screens.quizzes.groupeditems.GroupedItemsUiEvent.OnItemClicked
import com.exploramus.app.composables.screens.quizzes.utils.toAppColorSet
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.adaptive.layout
import com.exploramus.app.design.adaptive.useBottomBar
import com.exploramus.app.design.adaptive.value
import com.exploramus.app.design.theme.appColors
import com.exploramus.shared.viewmodel.screens.quizzes.groupeditems.MasteryItemState

@Composable
fun GroupedItemsGrid(
    gridItems: List<MasteryItemState>,
    onEvent: (GroupedItemsUiEvent) -> Unit,
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
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = gridItems,
                key = { it.id }
            ) { item ->
                GroupedGridCell(
                    item = item,
                    onClick = { onEvent(OnItemClicked(item)) },
                )
            }
        }
    }
}

@Composable
fun GroupedGridCell(
    modifier: Modifier = Modifier,
    item: MasteryItemState,
    onClick: () -> Unit,
) {
    val layout = MaterialTheme.layout.favorites

    Card(
        modifier = modifier.fillMaxWidth(),
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
                imageUrl = item.flagImage,
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    val statusColorSet = item.status.toAppColorSet()
                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(statusColorSet.background())
                            .border(
                                width = 1.dp,
                                color = statusColorSet.text().copy(alpha = 0.2f),
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = item.status.name.lowercase().replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.labelSmall,
                            color = statusColorSet.text(),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

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
