package com.muralex.exploramus.screens.details

import com.muralex.exploramus.viewmodel.core.Events
import com.muralex.exploramus.viewmodel.screens.countrydetail.toggleFavorite

sealed class DetailsUiEvent {
    data class ToggleFavorite(val countryId: String) : DetailsUiEvent()
}

class DetailsEventHandler(
    val events: Events
) {
    fun onEvent(event: DetailsUiEvent) {
        when (event) {
            is DetailsUiEvent.ToggleFavorite -> events.toggleFavorite(event.countryId)
        }
    }
}