package com.exploramus.app.composables.screens.details.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.exploramus.app.design.adaptive.layout
import com.exploramus.app.design.adaptive.value
import com.exploramus.app.resources.Strings
import com.exploramus.shared.viewmodel.screens.countrydetail.CountryDetailsState
import java.util.Locale

data class DetailsRowModel(
    val icon: ImageVector,
    val label: String,
    val value: String,
)

@Composable
fun detailRows(details: CountryDetailsState): List<DetailsRowModel> = listOf(
    DetailsRowModel(
        icon = Icons.Outlined.Map,
        label = Strings.detailLabelLocation,
        value = details.location,
    ),
    DetailsRowModel(
        icon = Icons.Outlined.Straighten,
        label = Strings.detailLabelArea,
        value = formatArea(details.area)
    ),
    DetailsRowModel(
        icon = Icons.Outlined.People,
        label = Strings.detailLabelPopulation,
        value = details.population.toHumanReadable()
    ),
    DetailsRowModel(
        icon = Icons.Outlined.Translate,
        label = Strings.detailLabelLanguage(details.languages.size),
        value = details.languages.joinToString()
    ),
    DetailsRowModel(
        icon = Icons.Outlined.AttachMoney,
        label = Strings.detailLabelCurrency,
        value = details.currency
    ),
    DetailsRowModel(
        icon = Icons.Outlined.Schedule,
        label = Strings.detailLabelTimezones,
        value = formatTimezones(details.timezones)
    ),
)

@Composable
fun DetailsScreenInfoRow(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    value: String
) {

    if (value.isBlank()) return

    val layout = MaterialTheme.layout.details

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = layout.infoRowVerticalPadding.value())
            .padding(end = 10.dp),
        verticalAlignment = Alignment.Top
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .padding(top = 6.dp)
                .size(28.dp)
                .alpha(0.8f)

        )

        Spacer(modifier = Modifier.width(layout.infoCardIconEndSpace.value()))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.alpha(0.7f)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
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