package com.muralex.exploramus.composables.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muralex.exploramus.resources.Strings

@Composable
fun SingleChoiceDialog(
    isVisible: Boolean,
    title: String = "",
    options: List<String> = emptyList(),
    selectedIndex: Int = 0,
    onOptionSelected: (Int) -> Unit,
    onDismiss: () -> Unit,

    dismissText: String = Strings.commonCancel,
) {
    if (!isVisible) return

    NoPaddingAlertDialog(
        titleText = title,
        content = {
            OptionsRadioGroup(
                options = options,
                selectedIndex = selectedIndex,
                onOptionSelected = {
                    onOptionSelected(it)
                    onDismiss()
                }
            )
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = dismissText,
                    fontSize = 15.sp,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    )

}


@Composable
fun OptionsRadioGroup(
    options: List<String>,
    selectedIndex: Int = 0,
    onOptionSelected: (Int) -> Unit = {},
) {
    val safeIndex = selectedIndex.coerceIn(0, options.lastIndex)
    var currentIndex by remember { mutableIntStateOf(safeIndex) }

    Column(Modifier.selectableGroup()) {
        options.forEachIndexed { index, option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .selectable(
                        selected = index == currentIndex,
                        onClick = {
                            currentIndex = index
                            onOptionSelected(index)
                        },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = index == currentIndex,
                    onClick = null
                )
                Text(
                    text = option,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}