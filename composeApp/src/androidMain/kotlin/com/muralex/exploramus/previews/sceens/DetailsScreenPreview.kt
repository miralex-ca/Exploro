package com.muralex.exploramus.previews.sceens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.muralex.exploramus.composables.screens.details.DetailsScreenContent
import com.muralex.exploramus.previews.PreviewCountryDetailsState
import com.muralex.exploramus.previews.PreviewScreen

private val previewDetails = PreviewCountryDetailsState.france

@Preview(name = "Details - Light")
@Composable
private fun DetailsScreenPreviewLight() {
    PreviewScreen {
        DetailsScreenContent(
            details = previewDetails,
            onEvent = {}
        )
    }
}


@Preview(name = "Details - Tablet: Dark", device = Devices.TABLET )
@Composable
private fun DetailsScreenPreviewDark() {
    PreviewScreen(dark = true) {
        DetailsScreenContent(
            details = previewDetails,
            onEvent = {}
        )
    }
}
