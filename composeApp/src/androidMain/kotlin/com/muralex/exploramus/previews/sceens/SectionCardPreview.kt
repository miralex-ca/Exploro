package com.muralex.exploramus.previews.sceens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.muralex.exploramus.composables.screens.section.CountryGridCard
import com.muralex.exploramus.previews.PreviewCard
import com.muralex.exploramus.previews.PreviewCountry

private val previewName = PreviewCountry.france.name
private val previewFlagUrl = PreviewCountry.france.flagPngUrl
private val previewSubregion = PreviewCountry.france.subregion

@Preview(name = "Card - Light", widthDp = 250)
@Composable
internal fun CountryGridCardPreviewLight() {
    PreviewCard {
        CountryGridCard(
            name = previewName,
            flagPngUrl = previewFlagUrl,
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
            flagPngUrl = previewFlagUrl,
            subregion = previewSubregion,
            onClick = {}
        )
    }
}