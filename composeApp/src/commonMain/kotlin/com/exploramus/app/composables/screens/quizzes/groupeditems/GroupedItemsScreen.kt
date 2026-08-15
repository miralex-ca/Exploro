package com.exploramus.app.composables.screens.quizzes.groupeditems

import androidx.compose.runtime.Composable
import com.exploramus.app.composables.components.EmptyState
import com.exploramus.app.composables.components.EmptyStateView
import com.exploramus.app.composables.components.FadeLoadingContent
import com.exploramus.app.composables.screens.quizzes.groupeditems.views.GroupedItemsGrid
import com.exploramus.app.composables.screens.quizzes.groupeditems.views.GroupedItemsList
import com.exploramus.app.design.adaptive.LocalFormFactor
import com.exploramus.app.design.adaptive.isCompact
import com.exploramus.core.models.QuizItemStatus
import com.exploramus.shared.viewmodel.screens.quizzes.groupeditems.GroupedItemsScreenState

@Composable
fun GroupedItemsScreen(
    screenState: GroupedItemsScreenState,
    eventHandler: GroupedItemsEventHandler
) {
    FadeLoadingContent(isLoading = screenState.isLoading) {
        GroupedItemsScreenContent(
            screenState = screenState,
            onEvent = eventHandler::onEvent,
        )
    }
}

@Composable
fun GroupedItemsScreenContent(
    screenState: GroupedItemsScreenState,
    onEvent: (GroupedItemsUiEvent) -> Unit,
) {
    val formFactor = LocalFormFactor.current
    val useGrid = !formFactor.isCompact

    if (screenState.items.isEmpty()) {
        if (screenState.masteryStatus == QuizItemStatus.UNKNOWN) {
            EmptyStateView(EmptyState.NotFound)
        } else {
            EmptyStateView(EmptyState.EmptyList)
        }

    } else {
        if (useGrid) {
            GroupedItemsGrid(
                gridItems = screenState.items,
                onEvent = onEvent
            )
        } else {
            GroupedItemsList(
                listItems = screenState.items,
                onEvent = onEvent
            )
        }
    }
}
