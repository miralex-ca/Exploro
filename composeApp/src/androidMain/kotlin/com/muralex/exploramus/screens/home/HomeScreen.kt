package com.muralex.exploramus.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.muralex.exploramus.ui.adaptive.LocalFormFactor
import com.muralex.exploramus.ui.adaptive.layout
import com.muralex.exploramus.ui.adaptive.useBottomBar
import com.muralex.exploramus.ui.adaptive.value
import com.muralex.exploramus.ui.components.EmptyState
import com.muralex.exploramus.ui.components.EmptyStateView
import com.muralex.exploramus.ui.components.FadeInScreenContent
import com.muralex.exploramus.ui.components.RemoteImage
import com.muralex.exploramus.ui.components.ScreenLoading
import com.muralex.exploramus.ui.theme.AppTypography
import com.muralex.exploramus.ui.theme.appColors
import com.muralex.exploramus.viewmodel.screens.home.HomeListItem
import com.muralex.exploramus.viewmodel.screens.home.HomeScreenState
import com.muralex.exploramus.viewmodel.screens.home.HomeSectionState

@Composable
fun HomeScreen(
    screenState: HomeScreenState,
    eventHandler: HomeEventHandler
) {
    if (screenState.isLoading) {
        ScreenLoading()
    } else {
        FadeInScreenContent(
            durationMillis = 200
        ) {

            HomeScreenContent(
                screenState = screenState,
                onEvent = eventHandler::onEvent,
            )
        }
    }
}

@Composable
fun HomeScreenContent(
    screenState: HomeScreenState,
    onEvent: (HomeUiEvent) -> Unit,
) {
    val formFactor = LocalFormFactor.current
    val layout = MaterialTheme.layout
    val bottomPadding = layout.home.bottomPadding.value() +
            if (formFactor.useBottomBar) 60.dp else 0.dp

    if (screenState.homeSections.isEmpty()) {
        EmptyStateView(EmptyState.EmptyList)
    } else {
        LazyColumn(
            contentPadding = PaddingValues(
                top = layout.home.topPadding.value(),
                bottom = bottomPadding,
            ),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            items(
                items = screenState.homeSections,
                key = { it.sectionName }
            ) { section ->
                HomeSectionRow(
                    section = section,
                    onListItemClick = { onEvent(HomeUiEvent.OnItemClicked(it)) },
                    onSectionClick = { onEvent(HomeUiEvent.OnSectionClicked(section)) }
                )
            }
        }

    }
}


@Composable
fun HomeSectionRow(
    section: HomeSectionState,
    onListItemClick: (HomeListItem) -> Unit,
    onSectionClick: () -> Unit
) {
    val layout = MaterialTheme.layout

    Column(
        modifier = Modifier
            .padding(bottom = 14.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onSectionClick)
                .padding(
                    start = layout.home.horizontalPadding.value(),
                    end = layout.home.horizontalPadding.value(),
                    top = 4.dp,
                    bottom = 4.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = section.sectionName,
                style = AppTypography.homeSectionTitle,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)

            )

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = layout.home.horizontalPadding.value()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = section.sectionListItems,
                key = { it.id }
            ) { item ->
                HomeSectionListCard(
                    item = item,
                    onClick = { onListItemClick(item) }
                )
            }
        }
    }
}

@Composable
fun HomeSectionListCard(
    item: HomeListItem,
    onClick: () -> Unit
) {
    val layout = MaterialTheme.layout

    Card(
        modifier = Modifier.width(layout.homeCard.width.value()),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.appColors.cardBorder
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            RemoteImage(
                imageUrl = item.flagPngUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(layout.homeCard.imageHeight.value())
                    .border(
                        width = 1.dp,
                        color = Color.LightGray.copy(alpha = 0.25f),
                        shape = RoundedCornerShape(4.dp)
                    ),
                shape = RoundedCornerShape(4.dp)
            )

            Column(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 2.dp)
                    .padding(horizontal = 4.dp)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
