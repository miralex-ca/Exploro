package com.muralex.myapp.screens.section

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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.muralex.models.CountryListItem
import com.muralex.myapp.navigation.AppNavigator
import com.muralex.myapp.theme.appColors
import com.muralex.myapp.ui.components.FadeInScreenContent
import com.muralex.myapp.viewmodel.screens.section.SectionScreenState


@Composable
fun SectionScreen(
    screenState: SectionScreenState,
    eventHandler: SectionEventHandler
) {
    FadeInScreenContent {
        SectionScreenContent(
            screenState = screenState,
            onListItemClick = { eventHandler.onEvent(SectionUiEvent.OnItemClicked(it)) },
        )
    }
}

@Composable
fun SectionScreenContent(
    screenState: SectionScreenState,
    onListItemClick: (CountryListItem) -> Unit,
) {
    when {
        screenState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Loading...")
            }
        }

        screenState.countries.isEmpty() -> {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No countries found")
            }
        }

        else -> {

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 160.dp),
                contentPadding = PaddingValues(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(screenState.countries) { item ->

                    CountryGridCard(
                        item = item,
                        onClick = {
                            onListItemClick(item)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CountryGridCard(
    item: CountryListItem,
    onClick: () -> Unit
) {

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors  = CardDefaults.cardColors(),
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

                AsyncImage(
                    model = item.flagPngUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .border(
                            width = 1.dp,
                            color = Color.LightGray.copy(alpha = 0.25f),
                            shape = RoundedCornerShape(6.dp)
                        ),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 4.dp, 12.dp,10.dp)
            ) {

                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = item.subregion,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.alpha(0.8f)
                )
            }
        }
    }
}