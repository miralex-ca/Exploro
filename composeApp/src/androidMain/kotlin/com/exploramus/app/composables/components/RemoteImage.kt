package com.exploramus.app.composables.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade


fun flagAssetUri(countryCode: String): String =
    "file:///android_asset/flags/w640/${countryCode.lowercase()}.png"

@Composable
fun RemoteImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    alpha: Float = 1f,
    shape: Shape = RectangleShape,
    usePlaceholder: Boolean = true
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .networkCachePolicy(CachePolicy.ENABLED)
            .build(),
        error = if (usePlaceholder) ColorPainter(Color.Gray.copy(alpha = 0.1f)) else null,
        placeholder = if (usePlaceholder) ColorPainter(Color.Gray.copy(alpha = 0.1f)) else null,
        contentDescription = contentDescription,
        modifier = modifier
            .clip(shape),
        contentScale = contentScale,
        alpha = alpha
    )
}

@Composable
fun ResourceImage(
    imageUri: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    alpha: Float = 1f,
    shape: Shape = RectangleShape,
    usePlaceholder: Boolean = true
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalPlatformContext.current)
            .data(imageUri)
            .crossfade(true)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .networkCachePolicy(CachePolicy.ENABLED)
            .build(),
        error = if (usePlaceholder) ColorPainter(Color.Gray.copy(alpha = 0.1f)) else null,
        placeholder = if (usePlaceholder) ColorPainter(Color.Gray.copy(alpha = 0.1f)) else null,
        contentDescription = contentDescription,
        modifier = modifier.clip(shape),
        contentScale = contentScale,
        alpha = alpha
    )
}
