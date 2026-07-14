package com.exploramus.app.composables.screens.quizzes.choicequiz.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import com.exploramus.app.composables.navigation.ui.topbars.topBarAdaptiveHeight
import com.exploramus.app.composables.screens.quizzes.choicequiz.ChoiceQuizUiEvent
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.theme.appColors
import com.exploramus.app.resources.Strings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChoiceQuizTopBar(
    title: String,
    onBackClick: () -> Unit,
    onEvent: (ChoiceQuizUiEvent) -> Unit,
) {
    val formFactor = LocalFormFactor.current

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
            IconButton(onClick = { onEvent(ChoiceQuizUiEvent.OnRestartClicked) }) {
                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = "Restart Quiz"
                )
            }
        }
    )
}
