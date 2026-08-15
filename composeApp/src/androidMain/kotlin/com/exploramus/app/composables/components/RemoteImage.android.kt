package com.exploramus.app.composables.components

import androidx.compose.runtime.Composable

@Composable
actual fun flagAssetUri(countryCode: String): String =
    "file:///android_asset/flags/w640/${countryCode.lowercase()}.png"
