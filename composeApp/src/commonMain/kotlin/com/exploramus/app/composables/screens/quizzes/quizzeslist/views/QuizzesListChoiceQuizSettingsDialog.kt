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
import com.exploramus.app.composables.screens.quizzes.utils.toTitle
import com.exploramus.app.resources.Strings
import com.exploramus.core.models.ChoiceQuizConfig
import com.exploramus.core.models.ChoiceQuizStudyTarget
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizType

@Composable
fun QuizzesListChoiceQuizSettingsDialog(
    quizType: QuizType,
    config: ChoiceQuizConfig,
    onConfigChanged: (ChoiceQuizConfig) -> Unit,
    onDismiss: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val limitOptions = listOf(20, 30, 40, null)

    val availableTargets = when (quizType) {
        QuizType.CHOICE_QUIZ_PRIMARY_SECONDARY -> listOf(
            ChoiceQuizStudyTarget.PRIMARY_SECONDARY,
            ChoiceQuizStudyTarget.SECONDARY_PRIMARY
        )
        QuizType.CHOICE_QUIZ_IMAGE_PRIMARY -> listOf(
            ChoiceQuizStudyTarget.IMAGE_PRIMARY,
            ChoiceQuizStudyTarget.PRIMARY_IMAGE
        )
        else -> emptyList()
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = Strings.choiceQuizSettingsTitle,
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = Strings.choiceQuizSelectPair,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                availableTargets.forEach { target ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onConfigChanged(config.copy(studyTarget = target)) }
                            .padding(vertical = 2.dp)
                    ) {
                        RadioButton(
                            selected = config.studyTarget == target,
                            onClick = { onConfigChanged(config.copy(studyTarget = target)) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = target.toTitle(),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = Strings.choiceQuizLimitLabel,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Normal,
                    )

                    Box {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable { expanded = true }
                                .padding(vertical = 4.dp)
                                .padding(start = 4.dp, end = 12.dp)
                        ) {
                            Text(
                                text = config.quizLimit?.toString() ?: Strings.choiceQuizLimitNoLimit,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .widthIn(min = 46.dp)
                                    .padding(start = 16.dp)
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
                            limitOptions.forEach { limit ->
                                DropdownMenuItem(
                                    text = {
                                        Text(limit?.toString() ?: Strings.choiceQuizLimitNoLimit)
                                    },
                                    onClick = {
                                        onConfigChanged(config.copy(quizLimit = limit))
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
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
