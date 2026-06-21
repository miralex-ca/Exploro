package com.exploramus.app.previews.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.exploramus.app.composables.components.ScreenLoading
import com.exploramus.app.previews.PreviewScreen

@Preview(name = "Loading - Light")
@Composable
internal fun ScreenLoadingPreviewLight() {
    PreviewScreen {
        ScreenLoading()
    }
}

@Preview(name = "Loading - Dark")
@Composable
internal fun ScreenLoadingPreviewDark() {
    PreviewScreen(dark = true) {
        ScreenLoading()
    }
}