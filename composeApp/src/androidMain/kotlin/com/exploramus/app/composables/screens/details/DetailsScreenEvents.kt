package com.exploramus.app.composables.screens.details

import com.exploramus.app.composables.navigation.controller.ScreenNavActions
import com.exploramus.shared.viewmodel.core.Events
import com.exploramus.shared.viewmodel.screens.details.singledetail.toggleFavorite

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