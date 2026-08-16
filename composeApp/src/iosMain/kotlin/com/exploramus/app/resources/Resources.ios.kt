package com.exploramus.app.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter

@Composable
actual fun painterResource(resName: String): Painter = ColorPainter(Color.Gray)

actual fun formatString(format: String, vararg args: Any): String {
    // Simple placeholder for iOS
    var result = format
    args.forEachIndexed { index, arg ->
        result = result.replace("%${index + 1}\$s", arg.toString())
            .replace("%s", arg.toString())
    }
    return result
}
