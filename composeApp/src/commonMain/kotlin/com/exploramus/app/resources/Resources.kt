package com.exploramus.app.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import exploramus.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource as cmpPainterResource

@Composable
fun painterResource(resName: String): Painter {
    val res = when (resName) {
        "wikipedia" -> Res.drawable.wikipedia
        "check_bold" -> Res.drawable.check_bold
        "close_bold" -> Res.drawable.close_bold
        "cards" -> Res.drawable.cards
        else -> Res.drawable.wikipedia
    }
    return cmpPainterResource(res)
}

expect fun formatString(format: String, vararg args: Any): String
