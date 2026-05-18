package com.muralex.myapp.screens.details

import com.muralex.myapp.viewmodel.Events
import com.muralex.myapp.viewmodel.screens.countrydetail.toggleFavorite

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