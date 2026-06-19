package com.muralex.exploramus.composables.screens.favorites

import androidx.compose.runtime.Composable
import com.muralex.exploramus.composables.screens.favorites.views.FavoritesGrid
import com.muralex.exploramus.composables.screens.favorites.views.FavoritesList
import com.muralex.exploramus.design.adaptive.LocalFormFactor
import com.muralex.exploramus.design.adaptive.isCompact
import com.muralex.exploramus.composables.components.EmptyState
import com.muralex.exploramus.composables.components.EmptyStateView
import com.muralex.exploramus.composables.components.FadeLoadingContent
import com.muralex.exploramus.viewmodel.screens.favorites.FavoritesScreenState


@Composable
fun FavoritesScreen(
    screenState: FavoritesScreenState,
    eventHandler: FavoritesEventHandler
) {
    FadeLoadingContent(isLoading = screenState.isLoading) {
        FavoritesScreenContent(
            screenState = screenState,
            onEvent = eventHandler::onEvent,
        )
    }
}

@Composable
fun FavoritesScreenContent(
    screenState: FavoritesScreenState,
    onEvent: (FavoritesUiEvent) -> Unit,
) {
    val formFactor = LocalFormFactor.current
    val useGrid = !formFactor.isCompact

    if (screenState.favorites.isEmpty()) {
        EmptyStateView(EmptyState.EmptyList)
    } else {
        if (useGrid) {
            FavoritesGrid(
                gridItems = screenState.favorites,
                onEvent = onEvent
            )
        } else {
            FavoritesList(
                listItems = screenState.favorites,
                onEvent = onEvent
            )
        }
    }
}
