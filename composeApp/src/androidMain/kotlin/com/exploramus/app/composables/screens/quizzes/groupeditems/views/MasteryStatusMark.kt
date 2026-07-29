package com.exploramus.app.composables.screens.quizzes.groupeditems.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.exploramus.app.composables.screens.quizzes.utils.toAppColorSet
import com.exploramus.core.models.QuizItemStatus

@Composable
fun MasteryStatusMark(
    modifier: Modifier = Modifier,
    status: QuizItemStatus,
) {
    val statusColorSet = status.toAppColorSet()
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(statusColorSet.background())
            .border(
                width = 1.dp,
                color = statusColorSet.text().copy(alpha = 0.2f),
                shape = RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            text = status.name.lowercase().replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.labelSmall,
            color = statusColorSet.text(),
            fontWeight = FontWeight.Medium
        )
    }
}
