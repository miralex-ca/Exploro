package com.exploramus.app.composables.screens.quizzes.flashcards.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.exploramus.core.models.FlashcardConfig
import com.exploramus.core.models.FlashcardStudyTarget

@Composable
fun FlashcardSettingsDialog(
    currentConfig: FlashcardConfig,
    onConfigChanged: (FlashcardConfig) -> Unit,
    onDismiss: () -> Unit,
) {
    var tempConfig by remember { mutableStateOf(currentConfig) }
    val isChanged = tempConfig != currentConfig
    var expanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Flashcard Settings",
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = true }
                            .padding(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "Visible clue",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Normal,
                        )
                        Box {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = tempConfig.studyTarget.displayLabel(),
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    maxLines = 1,
                                    modifier = Modifier.widthIn(min = 100.dp)
                                )
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                FlashcardStudyTarget.entries.forEach { field ->
                                    DropdownMenuItem(
                                        text = { Text(field.displayLabel()) },
                                        onClick = {
                                            tempConfig = tempConfig.copy(studyTarget = field)
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                    Text(
                        text = "Choose which information is displayed on the visible part of the card.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            tempConfig = tempConfig.copy(shuffleEnabled = !tempConfig.shuffleEnabled)
                        }
                        .padding(vertical = 8.dp),
                ) {
                    Text(
                        text = "Shuffle cards",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = tempConfig.shuffleEnabled,
                        onCheckedChange = {
                            tempConfig = tempConfig.copy(shuffleEnabled = it)
                        }
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            tempConfig = tempConfig.copy(revealEnabled = !tempConfig.revealEnabled)
                        }
                        .padding(vertical = 8.dp),
                ) {
                    Text(
                        text = "Reveal details",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = tempConfig.revealEnabled,
                        onCheckedChange = {
                            tempConfig = tempConfig.copy(revealEnabled = it)
                        }
                    )
                }
            }
        },
        dismissButton = {
            if (isChanged) {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        },
        confirmButton = {
            if (isChanged) {
                Button(
                    onClick = {
                        onConfigChanged(tempConfig)
                        onDismiss()
                    }
                ) {
                    Text("Restart")
                }
            } else {
                TextButton(onClick = onDismiss) {
                    Text("Done")
                }
            }
        },
    )
}

private fun FlashcardStudyTarget.displayLabel(): String = when (this) {
    FlashcardStudyTarget.PRIMARY -> "Country name"
    FlashcardStudyTarget.SECONDARY    -> "Capital city"
    FlashcardStudyTarget.IMAGE  -> "Country Flag"
}
