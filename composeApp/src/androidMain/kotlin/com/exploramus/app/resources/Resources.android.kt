package com.exploramus.app.resources

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.exploramus.app.R

@SuppressLint("DiscouragedApi")
@Composable
actual fun stringResource(id: String): String {
    val context = LocalContext.current
    val resourceId = context.resources.getIdentifier(id, "string", context.packageName)
    if (resourceId == 0) return id
    return context.getString(resourceId)
}

@SuppressLint("DiscouragedApi")
@Composable
actual fun stringResource(id: String, vararg formatArgs: Any): String {
    val context = LocalContext.current
    val resourceId = context.resources.getIdentifier(id, "string", context.packageName)
    if (resourceId == 0) return id
    return context.getString(resourceId, *formatArgs)
}

@SuppressLint("DiscouragedApi")
@Composable
actual fun painterResource(resName: String): Painter {
    val context = LocalContext.current
    val resourceId = context.resources.getIdentifier(resName, "drawable", context.packageName)
    return painterResource(if (resourceId != 0) resourceId else R.drawable.wikipedia)
}

actual fun formatString(format: String, vararg args: Any): String {
    return String.format(format, *args)
}
