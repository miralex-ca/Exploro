package com.exploramus.app.composables.screens.quizzes.flashcards.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.exploramus.app.resources.Strings
import kotlinx.coroutines.launch

@Composable
fun FlashcardNavBar(
    total: Int,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    isVertical: Boolean = false,
) {
    if (isVertical) {
        VerticalFlashcardNavBar(
            total = total,
            pagerState = pagerState,
            modifier = modifier
        )
    } else {
        HorizontalFlashcardNavBar(
            total = total,
            pagerState = pagerState,
            modifier = modifier
        )
    }
}

@Composable
private fun VerticalFlashcardNavBar(
    total: Int,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val currentPage = pagerState.currentPage

    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        tonalElevation = 2.dp,
        shadowElevation = 1.dp,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(28.dp, Alignment.CenterVertically),
            modifier = Modifier
                .padding(horizontal = 6.dp, vertical = 6.dp),
        ) {
            FilledIconButton(
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage((currentPage - 1).coerceAtLeast(0))
                    }
                },
                enabled = currentPage > 0,
                modifier = Modifier.size(width = 44.dp, height = 46.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = Strings.commonPrevious,
                    modifier = Modifier.size(28.dp),
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.width(44.dp)
            ) {
                Text(
                    text = "${currentPage + 1}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                )
                HorizontalDivider(
                    modifier = Modifier.width(20.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
                Text(
                    text = "$total",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                )
            }

            FilledIconButton(
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage((currentPage + 1).coerceAtMost(total - 1))
                    }
                },
                enabled = currentPage < total - 1,
                modifier = Modifier
                    .size(width = 44.dp, height = 46.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = Strings.commonNext,
                    modifier = Modifier.size(28.dp),
                )
            }
        }
    }
}

@Composable
private fun HorizontalFlashcardNavBar(
    total: Int,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val currentPage = pagerState.currentPage

    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        tonalElevation = 2.dp,
        shadowElevation = 1.dp,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
            modifier = Modifier
                .padding(horizontal = 6.dp, vertical = 6.dp),
        ) {
            FilledIconButton(
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage((currentPage - 1).coerceAtLeast(0))
                    }
                },
                enabled = currentPage > 0,
                modifier = Modifier.size(width = 46.dp, height = 44.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = Strings.commonPrevious,
                    modifier = Modifier.size(28.dp).offset(x = (-1).dp),
                )
            }

            Text(
                text = "${currentPage + 1} / $total",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .widthIn(min = 100.dp)
            )

            FilledIconButton(
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage((currentPage + 1).coerceAtMost(total - 1))
                    }
                },
                enabled = currentPage < total - 1,
                modifier = Modifier
                    .size(width = 46.dp, height = 44.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = Strings.commonNext,
                    modifier = Modifier
                        .size(28.dp)
                        .offset(x = 1.dp),
                )
            }
        }
    }
}
