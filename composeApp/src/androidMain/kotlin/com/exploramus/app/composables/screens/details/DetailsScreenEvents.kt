package com.exploramus.app.composables.screens.details

import com.exploramus.app.composables.navigation.controller.ScreenNavActions
import com.exploramus.shared.viewmodel.core.Events
import com.exploramus.shared.viewmodel.screens.details.detailpager.toggleFavoritePager
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

    fun onBackClicked() {
        navActions.navigateBack()
    }
}

sealed class DetailsPagerUiEvent {
    data class ToggleFavorite(val countryId: String) : DetailsPagerUiEvent()
    object OnBackClicked : DetailsPagerUiEvent()
}

class DetailsPagerEventHandler(
    val events: Events,
    val navActions: ScreenNavActions,
) {
    fun onEvent(event: DetailsPagerUiEvent) {
        when (event) {
            is DetailsPagerUiEvent.ToggleFavorite -> {
                events.toggleFavoritePager(event.countryId)
            }
            is DetailsPagerUiEvent.OnBackClicked -> navActions.navigateBack()
        }
    }
}