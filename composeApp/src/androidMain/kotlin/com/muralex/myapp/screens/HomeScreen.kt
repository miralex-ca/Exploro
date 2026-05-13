package com.muralex.myapp.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.muralex.models.CountryListItem
import com.muralex.myapp.R
import com.muralex.myapp.viewmodel.screens.home.HomeScreenState


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    screenState: HomeScreenState,
    onListItemClick: (CountryListItem) -> Unit
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
        if (screenState.countriesListItems.isEmpty()) {
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
            LazyColumn {
                items(items = screenState.countriesListItems, itemContent = { item ->
                    HomeScreenListRow(
                        item = item,
                        favorite = false,
                        onItemClick = {
                            onListItemClick(item)
                        },
                    )
                })
            }
        }
    }

}

@Composable
fun HomeScreenListRow(
    item: CountryListItem,
    favorite : Boolean,
    onItemClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick)
            .height(60.dp)
            .padding(start = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier
            .width(100.dp)
            .padding(end = 10.dp)) {

            val placeholderRes = when (item.id) {
                "AFG" -> R.drawable.taliban_flag
                else -> R.drawable.flag_placeholder
            }

            AsyncImage(
                model = item.flagPngUrl, // now guaranteed PNG
                contentDescription = null,
                error = painterResource(placeholderRes),
                modifier = Modifier
                    .clip(RoundedCornerShape(2.dp))
                    .height(50.dp)
                    .width(100.dp),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )



        }
        Column(modifier = Modifier
            .weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = item.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = item.subregion, style = MaterialTheme.typography.bodyMedium)

        }

        Column(modifier = Modifier
            .fillMaxHeight()
            .width(80.dp),
            horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            if (favorite) {
                Icon(Icons.Default.Star, contentDescription = "favorite", tint = Color.Magenta)
            } else {
                Icon(Icons.Default.Star, contentDescription = "not a favorite", tint = Color.LightGray)
            }
        }
    }
    HorizontalDivider()
}
