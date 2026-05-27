package com.muralex.exploramus.ui.components.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun NoPaddingAlertDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    titleText: String = "",
    title: @Composable (() -> Unit)? = null,
    content: @Composable (() -> Unit)? = null,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable (() -> Unit)? = null,
    shape: Shape = MaterialTheme.shapes.extraLarge,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    properties: DialogProperties = DialogProperties(),
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Surface(
            modifier = modifier,
            shape = shape,
            color = backgroundColor,
            contentColor = contentColor
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (titleText.isEmpty()) {
                    title?.let {
                        CompositionLocalProvider {
                            val textStyle = MaterialTheme.typography.titleLarge
                            ProvideTextStyle(textStyle, it)
                        }
                    }
                } else {
                    Text(
                        text = titleText,
                        modifier = Modifier
                            .padding(horizontal = 28.dp)
                            .padding(top = 26.dp, bottom = 24.dp),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 24.sp
                    )
                }
                content?.invoke()
                Box(
                    Modifier.fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End),
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 18.dp),
                    ) {
                        dismissButton?.invoke()
                        confirmButton()
                    }
                }
            }
        }
    }
}