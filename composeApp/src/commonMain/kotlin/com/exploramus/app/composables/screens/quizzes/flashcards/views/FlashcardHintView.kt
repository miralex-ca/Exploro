package com.exploramus.app.composables.screens.quizzes.flashcards.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.exploramus.app.resources.Strings
import com.exploramus.app.resources.painterResource

@Composable
fun FlashcardHintView(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(140.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                        shape = CircleShape,
                    )
            )
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                        shape = CircleShape,
                    )
            )
            Icon(
                painter = painterResource("cards"),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f),
                modifier = Modifier.size(70.dp),
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = Strings.flashcardHintTapToReveal,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}
