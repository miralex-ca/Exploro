package com.muralex.exploramus.previews.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.muralex.exploramus.composables.components.ScreenLoading
import com.muralex.exploramus.previews.PreviewScreen

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