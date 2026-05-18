package com.muralex.myapp.screens.favorites

import com.muralex.models.CountryListItem
import com.muralex.myapp.navigation.ScreenNavActions
import com.muralex.myapp.viewmodel.Events
import com.muralex.myapp.viewmodel.screens.favorites.removeFavoriteBySwipe

sealed class FavoritesUiEvent {
    data class RemoveFavorite(val countryId: String) : FavoritesUiEvent()
    data class OnItemClicked(val item: CountryListItem) : FavoritesUiEvent()
}

class FavoritesEventHandler(
    val navActions: ScreenNavActions,
    val events: Events
) {
    fun onEvent(event: FavoritesUiEvent) {
        when (event) {
            is FavoritesUiEvent.OnItemClicked -> navActions.toDetailFromList(event.item)
            is FavoritesUiEvent.RemoveFavorite -> events.removeFavoriteBySwipe(event.countryId)
        }
    }
}

