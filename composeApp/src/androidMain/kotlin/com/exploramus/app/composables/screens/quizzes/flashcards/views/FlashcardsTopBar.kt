package com.exploramus.app.composables.screens.quizzes.flashcards.views

import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextOverflow
import com.exploramus.app.composables.navigation.ui.topbars.topBarAdaptiveHeight
import com.exploramus.app.composables.screens.quizzes.flashcards.FlashcardUiEvent
import com.exploramus.app.design.adaptive.HeightType
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.theme.appColors
import com.exploramus.app.resources.Strings
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardsTopBar(
    title: String,
    onBackClick: () -> Unit,
    onEvent: (FlashcardUiEvent) -> Unit,
    pagerState: PagerState? = null,
) {
    val formFactor = LocalFormFactor.current
    val scope = rememberCoroutineScope()
    var showMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        expandedHeight = topBarAdaptiveHeight(formFactor),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.appColors.topBarContainer,
            titleContentColor = MaterialTheme.appColors.onTopBarContainer,
            navigationIconContentColor = MaterialTheme.appColors.onTopBarContainer,
            actionIconContentColor = MaterialTheme.appColors.onTopBarContainer,
        ),
        navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = Strings.commonBack,
                )
            }
        },
        actions = {
            if (formFactor.heightType == HeightType.COMPACT && pagerState != null) {
                val currentPage = pagerState.currentPage
                val pageCount = pagerState.pageCount

                IconButton(
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage((currentPage - 1).coerceAtLeast(0))
                        }
                    },
                    enabled = currentPage > 0,
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronLeft,
                        contentDescription = "Previous",
                    )
                }

                IconButton(
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage((currentPage + 1).coerceAtMost(pageCount - 1))
                        }
                    },
                    enabled = currentPage < pageCount - 1,
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Next",
                    )
                }
            }

            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More"
                )
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Cards settings") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = null
                        )
                    },
                    onClick = {
                        onEvent(FlashcardUiEvent.OnSettingsClicked)
                        showMenu = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Restart") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Refresh,
                            contentDescription = null
                        )
                    },
                    onClick = {
                        onEvent(FlashcardUiEvent.OnRestartClicked)
                        showMenu = false
                    }
                )
            }
        }
    )
}
