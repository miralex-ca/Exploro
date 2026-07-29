package com.exploramus.app.composables.screens.quizzes.groupeditems.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
fun GroupedItemsList(
    listItems: List<MasteryItemState>,
    onEvent: (GroupedItemsUiEvent) -> Unit,
) {
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
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = listItems,
                key = { it.id }
            ) { item ->
                GroupedItemRow(
                    modifier = Modifier.widthIn(max = layout.listItemMaxWidth.value()),
                    item = item,
                    onClick = { onEvent(OnItemClicked(item)) }
                )
            }
        }
    }
}

@Composable
fun GroupedItemRow(
    modifier: Modifier = Modifier,
    item: MasteryItemState,
    onClick: () -> Unit,
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RemoteImage(
                imageUrl = item.flagImage,
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
