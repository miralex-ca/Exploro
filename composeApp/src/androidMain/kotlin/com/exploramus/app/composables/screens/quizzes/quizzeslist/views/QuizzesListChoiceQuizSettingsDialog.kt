package com.exploramus.app.composables.screens.quizzes.quizzeslist.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = Strings.choiceQuizSelectPair,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                availableTargets.forEach { target ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onConfigChanged(config.copy(studyTarget = target)) }
                            .padding(vertical = 4.dp)
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
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(Strings.flashcardDone)
            }
        },
    )
}
