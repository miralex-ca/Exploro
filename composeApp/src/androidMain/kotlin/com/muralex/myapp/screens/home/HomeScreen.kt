package com.muralex.myapp.screens.home

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.muralex.models.CountryListItem
import com.muralex.models.HomeSection
import com.muralex.myapp.ui.theme.AppTypography
import com.muralex.myapp.ui.theme.appColors
import com.muralex.myapp.viewmodel.screens.home.HomeScreenState



@Composable
fun HomeScreen(
    screenState: HomeScreenState,
    eventHandler: HomeEventHandler
) {

    HomeScreenContent(
        screenState = screenState,
        onEvent = eventHandler::onEvent,
    )
}


@Composable
fun HomeScreenContent(
    screenState: HomeScreenState,
    onEvent: (HomeUiEvent) -> Unit,
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
        if (screenState.homeSections.isEmpty()) {
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
            LazyColumn(
                contentPadding = PaddingValues(top = 10.dp, bottom = 30.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                items(
                    screenState.homeSections,
                    key = { it.sectionName }
                ) { section ->
                    HomeSectionView(
                        section = section,
                        onListItemClick = { onEvent(HomeUiEvent.OnItemClicked(it))},
                        onSectionClick = { onEvent(HomeUiEvent.OnSectionClicked(section.sectionName))}
                    )
                }
            }
        }
    }
}


@Composable
fun HomeSectionView(
    section: HomeSection,
    onListItemClick: (CountryListItem) -> Unit,
    onSectionClick: () -> Unit
) {

    Column (
        modifier = Modifier
            .padding(bottom = 14.dp),
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onSectionClick)
                .padding(start = 25.dp, end = 20.dp, top = 4.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = section.sectionName,
                style = AppTypography.homeSectionTitle,
                modifier = Modifier.weight(1f)
            )

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    //.clickable(onClick = onSectionClick)
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
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                section.countries,
                key = { it.id }
            ) { item ->
                HomeCountryCard(
                    item = item,
                    onClick = {
                        onListItemClick(item)
                    }
                )
            }
        }

       // Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun HomeCountryCard(
    item: CountryListItem,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier.width(140.dp),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.appColors.cardBorder
        )
    ) {

        Column (
            modifier =  Modifier.padding(8.dp)
        ) {

            AsyncImage(
                model = item.flagPngUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .border(
                        width = 1.dp,
                        color = Color.LightGray.copy(alpha = 0.25f),
                        shape = RoundedCornerShape(6.dp)
                    )
                ,
                contentScale = ContentScale.Crop
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
