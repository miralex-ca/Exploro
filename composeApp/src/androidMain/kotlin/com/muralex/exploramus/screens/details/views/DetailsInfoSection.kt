package com.muralex.exploramus.screens.details.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.muralex.exploramus.ui.adaptive.layout
import com.muralex.exploramus.ui.adaptive.value
import com.muralex.exploramus.viewmodel.screens.countrydetail.CountryDetailsState

@Composable
fun DetailsInfoSection(
    details: CountryDetailsState
) {
    val layout = MaterialTheme.layout.details
    Column(
        modifier = Modifier.padding(
            horizontal = layout.infoCardHorizontalPadding.value(),
            vertical = layout.infoCardVerticalPadding.value(),
        )
    ) {
        detailRows(details).forEach { row ->
            DetailsScreenInfoRow(
                icon = row.icon,
                label = row.label,
                value = row.value,
                url = row.url,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Composable
fun LargeDetailsInfoSection(details: CountryDetailsState) {
    val layout = MaterialTheme.layout.details
    val rows = detailRows(details)
    val columns = 2
    Column(
        modifier = Modifier.padding(
            horizontal = layout.infoCardHorizontalPadding.value(),
            vertical = layout.infoCardVerticalPadding.value(),
        )
    ) {
        rows.chunked(columns).forEach { rowItems ->
            Row(modifier = Modifier.fillMaxWidth()) {
                rowItems.forEach { item ->
                    DetailsScreenInfoRow(
                        icon = item.icon,
                        label = item.label,
                        value = item.value,
                        url = item.url,
                        modifier = Modifier.weight(1f)
                    )
                }

                repeat(columns - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}