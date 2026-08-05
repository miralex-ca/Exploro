package com.exploramus.app.composables.screens.details.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.exploramus.app.composables.components.RemoteImage
import com.exploramus.app.composables.components.ResourceImage
import com.exploramus.app.composables.components.flagAssetUri
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.adaptive.isCompact
import com.exploramus.app.design.adaptive.layout
import com.exploramus.app.design.adaptive.value
import com.exploramus.app.design.theme.appColors
import com.exploramus.app.resources.Strings
import com.exploramus.shared.viewmodel.screens.details.CountryDetailsState

@Composable
fun DetailHeaderSection(
    details: CountryDetailsState,
    onFavoriteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FlagContainer(
            iso2 = details.iso2,
            flagAlt = details.flagEmoji,
            isFavorite = details.isFavorite,
            onFavoriteClick = onFavoriteClick
        )

        Spacer(modifier = Modifier.height(12.dp))

        CountryHeaderTitle(
            officialName = details.officialName,
            coatOfArmsUrl = details.coatOfArmsUrl,
            capital = details.capital,
            region = details.continent
        )
    }
}

@Composable
fun LargeDetailsHeaderSection(
    details: CountryDetailsState,
    onFavoriteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 10.dp, 10.dp, 24.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
                .padding(top = 12.dp)
        ) {
            CountryHeaderTitle(
                officialName = details.officialName,
                coatOfArmsUrl = details.coatOfArmsUrl,
                capital = details.capital,
                region = details.continent,
                isCenterAligned = false,
            )
        }

        Box(
            modifier = Modifier.weight(0.85f)
        ) {
            FlagContainer(
                iso2 = details.iso2,
                flagAlt = details.flagEmoji,
                isFavorite = details.isFavorite,
                onFavoriteClick = onFavoriteClick
            )
        }
    }
}

@Composable
fun FlagContainer(
    iso2: String,
    flagAlt: String?,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit
) {
    val formFactor = LocalFormFactor.current
    val alignFavoriteButton = if (formFactor.isCompact) Alignment.BottomEnd else Alignment.TopEnd
    val detailsLayout = MaterialTheme.layout.details
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(detailsLayout.imageCorner.value()))
    ) {

        ResourceImage(
            imageUri = flagAssetUri(iso2),
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

        ResourceImage(
            imageUri = flagAssetUri(iso2),
            contentDescription = flagAlt,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.85f)
                .height(detailsLayout.imageHeight.value()),
            shape = RoundedCornerShape(6.dp),
            usePlaceholder = false
        )

        FavoriteButton(
            isFavorite = isFavorite,
            onClick = onFavoriteClick,
            modifier = Modifier
                .align(alignFavoriteButton)
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
                tint = if (isFavorite) {
                    MaterialTheme.appColors.favorite
                } else Color.White,
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
    region: String,
    isCenterAligned: Boolean = true,
) {
    val detailsLayout = MaterialTheme.layout.details

    Column {
        Text(
            text = officialName,
            style = MaterialTheme.typography.headlineMedium,
            fontSize = detailsLayout.titleFontSize.value(),
            maxLines = 4,
            overflow = TextOverflow.Ellipsis,
            textAlign = if (isCenterAligned) TextAlign.Center else TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = if (isCenterAligned) Alignment.Center else Alignment.CenterStart
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(detailsLayout.coatsTextSpace.value()),
                verticalAlignment = Alignment.Top,
            ) {

                if (coatOfArmsUrl.isNotBlank()) {
                    CoatOfArmsImage(coatOfArmsUrl)
                }

                Column(
                    modifier = Modifier.padding(top = 4.dp, end = 15.dp)
                ) {
                    if (capital.isNotBlank()) {
                        InlineHeaderDetailRow(
                            label = Strings.detailLabelCapital,
                            value = capital.uppercase()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    InlineHeaderDetailRow(
                        label = Strings.detailLabelRegion,
                        value = region
                    )
                }
            }
        }
    }
}

@Composable
fun InlineHeaderDetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    labelWidth: Dp = 65.dp
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            modifier = Modifier.width(labelWidth),
            maxLines = 1,
            overflow = TextOverflow.Clip,
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

@Composable
fun CoatOfArmsImage(
    url: String
) {
    val detailsLayout = MaterialTheme.layout.details

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
        modifier = Modifier.size(detailsLayout.coatsOfArmsSize.value())
    ) {
        RemoteImage(
            imageUrl = url,
            contentScale = ContentScale.Fit,
            contentDescription = Strings.detailsCoatOfArms,
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            usePlaceholder = false
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
                        .padding(top = 30.dp, bottom = 20.dp),
                    contentAlignment = Alignment.Center
                ) {

                    RemoteImage(
                        imageUrl = url,
                        contentScale = ContentScale.Fit,
                        contentDescription = Strings.detailsCoatOfArms,
                        modifier = Modifier
                             .size(180.dp)
                            .padding(6.dp),
                        usePlaceholder = false
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