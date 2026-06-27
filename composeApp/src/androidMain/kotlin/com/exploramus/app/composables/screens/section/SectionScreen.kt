package com.exploramus.app.composables.screens.section

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.exploramus.app.composables.components.EmptyState
import com.exploramus.app.composables.components.EmptyStateView
import com.exploramus.app.composables.components.FadeInScreenContent
import com.exploramus.app.composables.components.RemoteImage
import com.exploramus.app.composables.components.ScreenLoading
import com.exploramus.app.design.adaptive.layout
import com.exploramus.app.design.adaptive.value
import com.exploramus.app.design.theme.appColors
import com.exploramus.shared.viewmodel.screens.section.SectionListItem
import com.exploramus.shared.viewmodel.screens.section.SectionScreenState


@Composable
fun SectionScreen(
    screenState: SectionScreenState,
    eventHandler: SectionEventHandler
) {
    FadeInScreenContent {
        if (screenState.isLoading) {
            ScreenLoading()
        } else {
            SectionScreenContent(
                screenState = screenState,
                onListItemClick = { eventHandler.onEvent(SectionUiEvent.OnItemClicked(it)) },
            )
        }
    }
}

@Composable
fun SectionScreenContent(
    screenState: SectionScreenState,
    onListItemClick: (SectionListItem) -> Unit,
) {
    val layout = MaterialTheme.layout

    if (screenState.countries.isEmpty()) {
        EmptyStateView(EmptyState.EmptyList)
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = layout.sectionCard.width.value()),
            contentPadding = PaddingValues(
                start = layout.section.horizontalPadding.value(),
                end = layout.section.horizontalPadding.value(),
                top = layout.section.topPadding.value(),
                bottom = layout.section.bottomPadding.value()
            ),
            horizontalArrangement = Arrangement.spacedBy(layout.section.cardSpacing.value()),
            verticalArrangement = Arrangement.spacedBy(layout.section.cardSpacing.value())
        ) {
            items(screenState.countries) { item ->
                CountryGridCard(
                    name = item.name,
                    flagPngUrl = item.flagImage,
                    subregion = item.location,
                    onClick = {
                        onListItemClick(item)
                    }
                )
            }
        }
    }
}

@Composable
fun CountryGridCard(
    name: String,
    flagPngUrl: String,
    subregion: String,
    onClick: () -> Unit
) {
    val layout = MaterialTheme.layout

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.appColors.cardBorder
        )
    ) {
        Column {
            Box(
                modifier = Modifier.padding(
                    start = 8.dp,
                    end = 8.dp,
                    top = 8.dp
                )
            ) {

                RemoteImage(
                    imageUrl = flagPngUrl,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(layout.sectionCard.imageHeight.value())
                        .border(
                            width = 1.dp,
                            color = Color.LightGray.copy(alpha = 0.25f),
                            shape = RoundedCornerShape(6.dp)
                        ),
                    shape = RoundedCornerShape(6.dp)
                )

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 4.dp, 12.dp, 10.dp)
            ) {

                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = subregion,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.alpha(0.8f)
                )
            }
        }
    }
}