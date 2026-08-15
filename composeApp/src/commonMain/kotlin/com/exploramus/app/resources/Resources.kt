package com.exploramus.app.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

@Composable
expect fun stringResource(id: String): String

@Composable
expect fun stringResource(id: String, vararg formatArgs: Any): String

@Composable
expect fun painterResource(resName: String): Painter

expect fun formatString(format: String, vararg args: Any): String
