package com.muralex.myapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Straighten
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.muralex.myapp.R
import com.muralex.myapp.viewmodel.screens.countrydetail.CountryDetailState

@Composable
fun CountryDetailScreen(
    countryDetailState: CountryDetailState,
) {

    if (countryDetailState.isLoading) return

    val country = countryDetailState.countryInfo?.country ?: return
    val details = countryDetailState.countryInfo?.details ?: return



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            shadowElevation = 8.dp,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {

            Column {
                CountryHeaderSection(
                    flagUrl = country.flagPngUrl,
                    flagAlt = country.flagAlt,
                    officialName = country.officialName,
                    coatOfArmsUrl = details.coatOfArmsPngUrl
                )

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    DetailRow(
                        icon = Icons.Outlined.LocationCity,
                        label = "Capital",
                        value = country.capital
                    )

                    DetailRow(
                        icon = Icons.Outlined.Map,
                        label = "Location",
                        value = country.subregion
                    )

                    DetailRow(
                        icon = Icons.Outlined.Straighten,
                        label = "Area",
                        value = formatArea(details.area)
                    )

                    DetailRow(
                        icon = Icons.Outlined.People,
                        label = "Population",
                        value = details.population.toString()
                    )

                    DetailRow(
                        icon = Icons.Outlined.AttachMoney,
                        label = "Currency",
                        value = "${details.currencySymbol} (${details.currencyName})"
                    )

                    DetailRow(
                        icon = Icons.Outlined.Translate,
                        label = "Languages",
                        value = details.languages.joinToString()
                    )

                    DetailRow(
                        icon = Icons.Outlined.Schedule,
                        label = "Time zones",
                        value = formatTimezones(details.timezones)
                    )

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
    coatOfArmsUrl: String?
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

       FlagHero(
           flagUrl = flagUrl,
           flagAlt = flagAlt
       )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = officialName,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (!coatOfArmsUrl.isNullOrBlank()) {
            AsyncImage(
                model = coatOfArmsUrl,
                contentDescription = null,
                error = painterResource(R.drawable.flag_placeholder),
                modifier = Modifier.height(70.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun FlagHero(
    flagUrl: String,
    flagAlt: String?
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {


        AsyncImage(
            model = flagUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .blur(30.dp),
            contentScale = ContentScale.Crop,
            alpha = 0.45f
        )

        // dark overlay for contrast (optional but recommended)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.05f))
        )

        AsyncImage(
            model = flagUrl,
            contentDescription = flagAlt,
            error = painterResource(R.drawable.flag_placeholder),
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.85f)
                .height(140.dp)
                .clip(RoundedCornerShape(6.dp)),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun DetailRow(
    icon: ImageVector,
    label: String,
    value: String,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .size(28.dp),
            tint = Color.Black
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )

            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
    }
}

fun formatArea(area: Double): String {
    return when {
        area >= 1_000_000 -> "${"%.1f".format(area / 1_000_000)}M km²"
        area >= 1_000 -> "${"%.1f".format(area / 1_000)}K km²"
        else -> "${area.toInt()} km²"
    }
}

fun formatTimezones(timezones: List<String>): String {
    if (timezones.isEmpty()) return "N/A"

    return when {
        timezones.size == 1 -> timezones.first()
        timezones.size <= 3 -> timezones.joinToString(", ")
        else -> "${timezones.take(2).joinToString(", ")} +${timezones.size - 2} more"
    }
}