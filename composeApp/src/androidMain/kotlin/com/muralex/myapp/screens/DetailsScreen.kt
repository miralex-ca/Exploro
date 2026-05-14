package com.muralex.myapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.muralex.myapp.R
import com.muralex.myapp.viewmodel.screens.countrydetail.DetailsState
import java.util.Locale

@Composable
fun DetailsScreen(
    screenState: DetailsState,
) {

    if (screenState.isLoading) return

    val country = screenState.countryDetails?.country ?: return
    val details = screenState.countryDetails?.details ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            shadowElevation = 8.dp,
            modifier = Modifier
                .widthIn(max = 700.dp)
                .padding(16.dp)
        ) {

            Column {
                CountryHeaderSection(
                    flagUrl = country.flagPngUrl,
                    flagAlt = country.flagAlt,
                    officialName = country.officialName,
                    coatOfArmsUrl = details.coatOfArmsPngUrl,
                    capital = country.capital,
                    languages = details.languages,
                    continent = country.continent
                )

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier.padding( horizontal = 24.dp, vertical = 20.dp)
                ) {

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
                        value = details.population.toHumanReadable()
                    )

                    DetailRow(
                        icon = Icons.Outlined.Translate,
                        label = "Official languages",
                        value = details.languages.joinToString()
                    )


                    DetailRow(
                        icon = Icons.Outlined.AttachMoney,
                        label = "Currency",
                        value = "${details.currencySymbol} (${details.currencyName})"
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
    coatOfArmsUrl: String?,
    capital: String,
    languages: List<String>,
    continent: String
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
            tint = Color.Black.copy(alpha = 0.8f)
        )

        Spacer(modifier = Modifier.width(28.dp))

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
                fontWeight = FontWeight.Normal,
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



fun Long.toHumanReadable(): String {
    fun Double.clean(): String =
        if (this % 1.0 == 0.0) {
            this.toInt().toString()
        } else {
            String.format(Locale.getDefault(), "%.1f", this)
        }

    return when {
        this >= 1_000_000_000 ->
            "${(this / 1_000_000_000.0).clean()}B"

        this >= 1_000_000 ->
            "${(this / 1_000_000.0).clean()}M"

        this >= 1_000 ->
            "${(this / 1_000.0).clean()}K"

        else -> this.toString()
    }
}

//@SuppressLint("UnusedBoxWithConstraintsScope")
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
                .padding( horizontal = 16.dp, vertical = 6.dp)
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
                        InlineDetailRow(
                            label = "Capital:",
                            value = capital.uppercase(Locale.getDefault())
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    InlineDetailRow(
                        label = "Region:",
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
        color = Color(0xFFF3F3F3),
        border = BorderStroke(
            0.5.dp,
            Color.Black.copy(alpha = 0.08f)
        ),
        modifier = Modifier.size(52.dp)
    ) {

        AsyncImage(
            model = url,
            contentDescription = "Coat of arms",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp)
        )
    }

    if (showDialog) {

        AlertDialog(
            onDismissRequest = { showDialog = false },
            modifier = Modifier.padding(10.dp),

            title = {
                Text("Coat of Arms")
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
                        contentDescription = "Coat of arms",
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
                    Text("CLOSE")
                }
            },

            tonalElevation = 1.dp
        )
    }
}

@Composable
fun InlineDetailRow(
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
           // textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = value,
           // modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

