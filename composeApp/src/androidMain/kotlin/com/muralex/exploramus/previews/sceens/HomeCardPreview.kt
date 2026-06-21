package com.muralex.exploramus.previews.sceens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.muralex.exploramus.composables.screens.home.HomeSectionListCard
import com.muralex.exploramus.previews.PreviewCard
import com.muralex.exploramus.previews.PreviewCountry


private val previewName = PreviewCountry.france.name
private val previewFlagUrl = PreviewCountry.france.flagPngUrl

@Preview(name = "Card - Light")
@Composable
fun HomeSectionListCardPreviewLight() {
    PreviewCard {
        HomeSectionListCard(
            name = previewName,
            flagPngUrl = previewFlagUrl,
            onClick = {}
        )
    }
}

@Preview(name = "Card - Dark")
@Composable
fun HomeSectionListCardPreviewDark() {
    PreviewCard(dark = true) {
        HomeSectionListCard(
            name = previewName,
            flagPngUrl = previewFlagUrl,
            onClick = {}
        )
    }
}