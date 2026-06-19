package com.muralex.exploramus.composables.screens.favorites

import com.muralex.exploramus.composables.navigation.controller.DetailsNavParams
import com.muralex.exploramus.composables.navigation.controller.ScreenNavActions
import com.muralex.exploramus.viewmodel.core.Events
import com.muralex.exploramus.viewmodel.screens.favorites.FavoriteListItem
import com.muralex.exploramus.viewmodel.screens.favorites.removeFavoriteBySwipe

sealed class FavoritesUiEvent {
    data class RemoveFavorite(val countryId: String) : FavoritesUiEvent()
    data class OnItemClicked(val item: FavoriteListItem) : FavoritesUiEvent()
}

class FavoritesEventHandler(
    val navActions: ScreenNavActions,
    val events: Events
) {
    fun onEvent(event: FavoritesUiEvent) {
        when (event) {
            is FavoritesUiEvent.OnItemClicked -> navActions.toDetailFromList(event.item.toDetailsNavParams())
            is FavoritesUiEvent.RemoveFavorite -> events.removeFavoriteBySwipe(event.countryId)
        }
    }
}

fun FavoriteListItem.toDetailsNavParams() = DetailsNavParams(id, name)

