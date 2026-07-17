package com.exploramus.app.composables.screens.quizzes.choicequiz.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextOverflow
import com.exploramus.app.composables.navigation.ui.topbars.topBarAdaptiveHeight
import com.exploramus.app.composables.screens.quizzes.choicequiz.ChoiceQuizUiEvent
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.theme.appColors
import com.exploramus.app.resources.Strings
import com.exploramus.core.models.ChoiceQuizNavigationMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChoiceQuizTopBar(
    title: String,
    navigationMode: ChoiceQuizNavigationMode,
    onBackClick: () -> Unit,
    onEvent: (ChoiceQuizUiEvent) -> Unit,
) {
    val formFactor = LocalFormFactor.current
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
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = Strings.commonMore
                )
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text(Strings.flashcardRestart) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Refresh,
                            contentDescription = null
                        )
                    },
                    onClick = {
                        onEvent(ChoiceQuizUiEvent.OnRestartClicked)
                        showMenu = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Auto-advance") },
                    leadingIcon = {
                        val icon = if (navigationMode == ChoiceQuizNavigationMode.AUTO) {
                            Icons.Default.CheckBox
                        } else {
                            Icons.Default.CheckBoxOutlineBlank
                        }
                        Icon(imageVector = icon, contentDescription = null)
                    },
                    onClick = {
                        onEvent(ChoiceQuizUiEvent.OnToggleNavigationMode)
                        showMenu = false
                    }
                )
            }
        }
    )
}
