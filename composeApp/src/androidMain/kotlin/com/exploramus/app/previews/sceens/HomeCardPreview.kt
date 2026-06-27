package com.exploramus.app.previews.sceens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.exploramus.app.composables.screens.home.HomeSectionListCard
import com.exploramus.app.previews.PreviewCard
import com.exploramus.app.previews.PreviewCountry


private val previewName = PreviewCountry.france.name
private val previewFlagUrl = PreviewCountry.france.flagImage

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
