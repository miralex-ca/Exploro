package com.muralex.myapp.screens.details

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.muralex.myapp.resources.Strings
import com.muralex.myapp.screens.details.DetailsUiEvent.ToggleFavorite
import com.muralex.myapp.ui.components.EmptyState
import com.muralex.myapp.ui.components.EmptyStateView
import com.muralex.myapp.ui.components.FadeInScreenContent
import com.muralex.myapp.ui.components.RemoteImage
import com.muralex.myapp.ui.components.ScreenLoading
import com.muralex.myapp.ui.theme.appColors
import com.muralex.myapp.viewmodel.screens.countrydetail.CountryDetailsState
import com.muralex.myapp.viewmodel.screens.countrydetail.DetailsScreenState
import java.util.Locale

@Composable
fun DetailsScreen(
    screenState: DetailsScreenState,
    eventHandler: DetailsEventHandler
) {
    val details = screenState.details

    FadeInScreenContent {
        when {
            screenState.isLoading -> {
                ScreenLoading()
            }
            details == null -> {
                EmptyStateView(EmptyState.NotFound)
            }
            else -> {
                DetailsScreenContent(
                    details = details,
                    onEvent = eventHandler::onEvent
                )
            }
        }
    }
}

@Composable
fun DetailsNotFoundScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Surface(
            modifier = Modifier
                .padding(24.dp)
                .widthIn(max = 360.dp)
                .padding(top = 30.dp),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = 28.dp,
                    vertical = 32.dp
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    imageVector = Icons.Outlined.SearchOff,
                    contentDescription = null,
                    modifier = Modifier.size(42.dp).alpha(0.8f),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Details not available",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "We couldn't find information for this item.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun DetailsScreenContent(
    details: CountryDetailsState,
    onEvent: (DetailsUiEvent) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surfaceContainerHighest,
            contentColor = MaterialTheme.colorScheme.onSurface,
            shadowElevation = 0.dp,
            modifier = Modifier
                .widthIn(max = 700.dp)
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp, bottom = 60.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.appColors.cardBorder,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {

            Column {
                CountryHeaderSection(
                    flagUrl = details.flagUrl,
                    flagAlt = details.flagAlt,
                    officialName = details.officialName,
                    coatOfArmsUrl = details.coatOfArmsUrl,
                    capital = details.capital,
                    continent = details.continent,
                    isFavorite = details.isFavorite,
                    onFavoriteClick = { onEvent(ToggleFavorite(details.id)) }
                )

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier.padding( horizontal = 24.dp, vertical = 20.dp)
                ) {

                    detailRows(details).forEach { row ->
                        DetailsScreenInfoRow(
                            icon = row.icon,
                            label = row.label,
                            value = row.value,
                            url = row.url
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                }
            }
        }
    }
}

@Composable
fun CountryHeaderSection(
    flagUrl: String,
    flagAlt: String?,
    officialName: String,
    coatOfArmsUrl: String?,
    capital: String,
    continent: String,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        FlagHero(
            flagUrl = flagUrl,
            flagAlt = flagAlt,
            isFavorite = isFavorite,
            onFavoriteClick = onFavoriteClick
        )

        Spacer(modifier = Modifier.height(12.dp))

        CountryHeaderTitle(
            officialName = officialName,
            coatOfArmsUrl = coatOfArmsUrl ?: "",
            capital = capital,
            continent = continent
        )

    }
}

@Composable
fun FlagHero(
    flagUrl: String,
    flagAlt: String?,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {

        RemoteImage(
            imageUrl = flagUrl,
            contentDescription = flagAlt,
            modifier = Modifier
                .fillMaxSize()
                .blur(30.dp),
            contentScale = ContentScale.Crop,
            alpha = 0.45f
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.appColors.detailHederWrapper)
        )

        RemoteImage(
            imageUrl = flagUrl,
            contentDescription = flagAlt,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.85f)
                .height(140.dp),
            shape = RoundedCornerShape(6.dp),
        )

        FavoriteButton(
            isFavorite = isFavorite,
            onClick = onFavoriteClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(6.dp)
        )

    }
}

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .size(48.dp),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.42f))
                .clickable(onClick = onClick),

            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = if (isFavorite) {
                    Icons.Rounded.Star
                } else {
                    Icons.Rounded.StarBorder
                },
                contentDescription = null,
                tint = if (isFavorite) { MaterialTheme.appColors.favorite } else Color.White,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

@Composable
fun CountryHeaderTitle(
    officialName: String,
    coatOfArmsUrl: String,
    capital: String,
    continent: String
) {
    Column {
        Text(
            text = officialName,
            style = MaterialTheme.typography.headlineMedium,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.Top,
            ) {

                if (coatOfArmsUrl.isNotBlank()) {
                    CoatOfArmsImage(coatOfArmsUrl)
                }

                Column (
                    modifier = Modifier.padding(end = 15.dp)
                ) {
                    if (capital.isNotBlank()) {
                        InlineHeaderDetailRow(
                            label = Strings.detailLabelCapital,
                            value = capital.uppercase(Locale.getDefault())
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    InlineHeaderDetailRow(
                        label = Strings.detailLabelRegion,
                        value = continent
                    )
                }
            }
        }
    }
}

@Composable
fun CoatOfArmsImage(
    url: String
) {
    var showDialog by remember {
        mutableStateOf(false)
    }

    Surface(
        onClick = {
            showDialog = true
        },
        shape = RoundedCornerShape(8.dp),
        color = Color(0xADD5DEEC),
        border = BorderStroke(
            0.5.dp,
            Color(0xD8C9D3E3)
        ),
        modifier = Modifier.size(52.dp)
    ) {

        RemoteImage(
            imageUrl = url,
            contentScale = ContentScale.Fit,
            contentDescription = Strings.detailsCoatOfArms,
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            modifier = Modifier.padding(10.dp),

            title = {
                Text(Strings.detailsCoatOfArms)
            },

            text = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp, bottom = 20.dp)
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = url,
                        contentDescription = Strings.detailsCoatOfArms,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(180.dp)
                    )
                }
            },

            confirmButton = {
                TextButton(
                    onClick = { showDialog = false },
                    contentPadding = PaddingValues(horizontal = 18.dp, vertical = 0.dp)
                ) {
                    Text(Strings.commonClose)
                }
            },

            tonalElevation = 1.dp
        )
    }
}

@Composable
fun InlineHeaderDetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    labelWidth: Dp = 55.dp
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            modifier = Modifier.width(labelWidth),
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

