package com.muralex.myapp.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.muralex.models.Country
import com.muralex.models.CountryListItem
import com.muralex.myapp.viewmodel.screens.countriesList.CountriesListItem
import com.muralex.myapp.viewmodel.screens.countriesList.CountriesListState
import com.muralex.myapp.R


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountriesListScreen(
    countriesListState: CountriesListState,
    onListItemClick: (CountryListItem) -> Unit,
    onFavoriteIconClick : (String) -> Unit,
) {
    if (countriesListState.isLoading) {

        // LoadingScreen()

    } else {
        if (countriesListState.countriesListItems.isEmpty()) {
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
                items(items = countriesListState.countriesListItems, itemContent = { item ->
                    CountriesListRow(
                        item = item,
                        favorite = true,
                        onItemClick = {
                            onListItemClick(item._data)
                         },
                        onFavoriteIconClick = {
                           // onFavoriteIconClick(item.name)
                        })
                })
            }
        }
    }

}

@Composable
fun CountriesListHeader() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .background(MaterialTheme.colorScheme.primaryContainer)
        .padding(start = 10.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "country", fontSize = 16.sp)
        }
        Column(modifier = Modifier.width(70.dp), horizontalAlignment = Alignment.End) {
            Text(text = "first\ndose", fontSize = 15.sp, textAlign = TextAlign.Right)
        }
        Column(modifier = Modifier.width(70.dp), horizontalAlignment = Alignment.End) {
            Text(text = "fully\nvax'd", fontSize = 15.sp, textAlign = TextAlign.Right)
        }
        Column(modifier = Modifier.width(100.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "favorite?", fontSize = 15.sp, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun CountriesListRow(
    item: CountriesListItem,
    favorite : Boolean,
    onItemClick: () -> Unit,
    onFavoriteIconClick: () -> Unit,
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
            .weight(1f)
            .padding(end = 10.dp)) {

            val placeholderRes = when (item._data.id) {
                "AFG" -> R.drawable.taliban_flag
                else -> R.drawable.flag_placeholder
            }


            AsyncImage(
                model = item._data.flagPngUrl, // now guaranteed PNG
                contentDescription = null,
                error = painterResource(placeholderRes),
                modifier = Modifier
                    .clip(RoundedCornerShape(2.dp))
                    .height(50.dp)
                    .width(80.dp),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )



        }
        Column(modifier = Modifier.width(200.dp), horizontalAlignment = Alignment.Start) {
            Text(text = item.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = item._data.subregion, style = MaterialTheme.typography.bodyMedium)

        }

        Column(modifier = Modifier
            .fillMaxHeight()
            .width(100.dp)
            .clickable(onClick = onFavoriteIconClick), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            if (favorite) {
                Icon(Icons.Default.Star, contentDescription = "favorite", tint = Color.Magenta)
            } else {
                Icon(Icons.Default.Star, contentDescription = "not a favorite", tint = Color.LightGray)
            }
        }
    }
    HorizontalDivider()
}
