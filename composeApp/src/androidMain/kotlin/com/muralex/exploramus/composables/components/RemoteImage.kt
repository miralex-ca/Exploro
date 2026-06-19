package com.muralex.exploramus.composables.components



import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.muralex.exploramus.R


@Composable
fun RemoteImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    alpha: Float = 1f,
    shape: Shape = RectangleShape,
) {
    val placeholderRes = when {
        imageUrl?.lowercase()?.contains("taliban") == true  -> R.drawable.taliban_flag
        else -> R.drawable.flag_placeholder
    }

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .networkCachePolicy(CachePolicy.ENABLED)
            .build(),
        error = painterResource(placeholderRes),
        contentDescription = contentDescription,
        modifier = modifier
            .clip(shape),
        contentScale = contentScale,
        alpha = alpha
    )
}