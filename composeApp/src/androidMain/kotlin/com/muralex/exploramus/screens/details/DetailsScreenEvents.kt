package com.muralex.exploramus.screens.details

import com.muralex.exploramus.navigation.controller.ScreenNavActions
import com.muralex.exploramus.viewmodel.core.Events
import com.muralex.exploramus.viewmodel.screens.countrydetail.toggleFavorite

sealed class DetailsUiEvent {
    data class ToggleFavorite(val countryId: String) : DetailsUiEvent()
    object OnBackClicked : DetailsUiEvent()
}

class DetailsEventHandler(
    val events: Events,
    val navActions: ScreenNavActions,
) {
    fun onEvent(event: DetailsUiEvent) {
        when (event) {
            is DetailsUiEvent.ToggleFavorite -> events.toggleFavorite(event.countryId)
            is DetailsUiEvent.OnBackClicked -> navActions.navigateBack()
        }
    }
}