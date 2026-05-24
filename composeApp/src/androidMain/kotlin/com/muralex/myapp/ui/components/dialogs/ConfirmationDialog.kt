package com.muralex.myapp.ui.components.dialogs

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muralex.myapp.utils.Strings

@Composable
fun ConfirmationDialog(
    isVisible: Boolean,
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,

    confirmText: String = Strings.commonConfirm,
    dismissText: String = Strings.commonCancel,
) {

    if (!isVisible) return

    NoPaddingAlertDialog(
        titleText = title,
        content = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(top = 0.dp, bottom = 12.dp)
            )
        },
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = dismissText,
                    fontSize = 15.sp,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                    onConfirm()
                }
            ) {
                Text(
                    text = confirmText,
                    fontSize = 15.sp,
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
    )
}