package com.exploramus.app.previews.sceens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.exploramus.app.composables.screens.section.CountryGridCard
import com.exploramus.app.previews.PreviewCard
import com.exploramus.app.previews.PreviewCountry

private val previewName = PreviewCountry.france.name
private val previewIso2 = PreviewCountry.france.iso2
private val previewSubregion = PreviewCountry.france.location

@Preview(name = "Card - Light", widthDp = 250)
@Composable
internal fun CountryGridCardPreviewLight() {
    PreviewCard {
        CountryGridCard(
            name = previewName,
            iso2 = previewIso2,
            subregion = previewSubregion,
            onClick = {}
        )
    }
}

@Preview(name = "Card - Dark", widthDp = 250)
@Composable
fun CountryGridCardPreviewDark() {
    PreviewCard(dark = true) {
        CountryGridCard(
            name = previewName,
            iso2 = previewIso2,
            subregion = previewSubregion,
            onClick = {}
        )
    }
}
