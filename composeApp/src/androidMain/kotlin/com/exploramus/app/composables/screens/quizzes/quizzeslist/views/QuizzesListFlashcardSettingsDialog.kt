package com.exploramus.app.composables.screens.quizzes.quizzeslist.views

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
import com.exploramus.app.resources.Strings
import com.exploramus.core.models.FlashcardConfig
import com.exploramus.core.models.FlashcardStudyTarget

@Composable
fun QuizzesListFlashcardSettingsDialog(
    config: FlashcardConfig,
    onConfigChanged: (FlashcardConfig) -> Unit,
    onDismiss: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = Strings.flashcardSettingsTitle,
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
                            text = Strings.flashcardVisibleClue,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Normal,
                        )
                        Box {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = config.studyTarget.displayLabel(),
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
                                            onConfigChanged(config.copy(studyTarget = field))
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                    Text(
                        text = Strings.flashcardSettingsDescription,
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
                            onConfigChanged(config.copy(shuffleEnabled = !config.shuffleEnabled))
                        }
                        .padding(vertical = 8.dp),
                ) {
                    Text(
                        text = Strings.flashcardShuffleCards,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = config.shuffleEnabled,
                        onCheckedChange = {
                            onConfigChanged(config.copy(shuffleEnabled = it))
                        }
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onConfigChanged(config.copy(revealEnabled = !config.revealEnabled))
                        }
                        .padding(vertical = 8.dp),
                ) {
                    Text(
                        text = Strings.flashcardRevealDetails,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = config.revealEnabled,
                        onCheckedChange = {
                            onConfigChanged(config.copy(revealEnabled = it))
                        }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(Strings.flashcardDone)
            }
        },
    )
}

@Composable
private fun FlashcardStudyTarget.displayLabel(): String = when (this) {
    FlashcardStudyTarget.PRIMARY -> Strings.flashcardTargetPrimary
    FlashcardStudyTarget.SECONDARY    -> Strings.flashcardTargetSecondary
    FlashcardStudyTarget.IMAGE  -> Strings.flashcardTargetImage
}
