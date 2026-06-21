package com.exploramus.app.composables.screens.search.views

import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
import com.exploramus.app.resources.Strings
import com.exploramus.app.design.adaptive.layout
import com.exploramus.app.design.adaptive.value
import com.exploramus.app.composables.components.RemoteImage
import com.exploramus.app.design.theme.appColors
import com.exploramus.shared.viewmodel.screens.search.SearchListItem
import com.exploramus.shared.viewmodel.screens.search.SearchResult

@Composable
fun SearchResultsList(
    listState: LazyListState,
    result: SearchResult.Success,
    onItemClicked: (SearchListItem) -> Unit
) {
    LazyColumn(
        state = listState,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.layout.cardSpacing.value()),
        contentPadding = PaddingValues(
            start = 14.dp,
            end = 14.dp,
            top = 12.dp,
            bottom = 60.dp
        ),
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(
            items = result.items,
            key = { _, item -> item.id }
        ) { index, item ->
            SearchListRow(
                item = item,
                index = index,
                lastIndex = result.items.lastIndex,
                onClick = { onItemClicked(item) },
                modifier = Modifier.animateItem(
                    fadeInSpec = tween(180),
                    fadeOutSpec = tween(180),
                    placementSpec = tween(180)
                )
            )
        }
    }
}

@Composable
fun SearchListRow(
    item: SearchListItem,
    index: Int,
    lastIndex: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val layout = MaterialTheme.layout.search

    val isFirst = index == 0
    val isLast = index == lastIndex

    val shape = when {
        isFirst && isLast -> RoundedCornerShape(12.dp)
        isFirst -> RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
        isLast -> RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
        else -> RoundedCornerShape(0.dp)
    }

    Card(
        modifier = modifier
            .widthIn(max = layout.listItemMaxWidth.value()),
        shape = shape,
        border = BorderStroke(
            width = 0.5.dp,
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
                    .height(58.dp)
                    .width(layout.itemImageWidth.value())
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
                    text = item.officialName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = Strings.listItemLabelCapital(item.capital),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}