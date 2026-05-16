package com.muralex.myapp.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.muralex.models.CountryListItem
import com.muralex.models.HomeSection
import com.muralex.myapp.viewmodel.screens.home.HomeScreenState


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    screenState: HomeScreenState,
    onListItemClick: (CountryListItem) -> Unit,
    onSectionClick: (String) -> Unit
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
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {

                items(screenState.homeSections) { section ->

                    HomeSectionView(
                        section = section,
                        onListItemClick = onListItemClick,
                        onSectionClick = {
                            onSectionClick(section.continent)
                        }
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

    Column {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = section.continent,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )

            TextButton(
                onClick = onSectionClick
            ) {
                Text("See all")
            }
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(section.countries) { item ->

                HomeCountryCard(
                    item = item,
                    onClick = {
                        onListItemClick(item)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun HomeCountryCard(
    item: CountryListItem,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .width(130.dp),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp)
    ) {

        Column {

            AsyncImage(
                model = item.flagPngUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(8.dp)
            ) {

                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1
                )

            }
        }
    }
}
