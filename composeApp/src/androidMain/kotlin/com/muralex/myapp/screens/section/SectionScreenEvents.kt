package com.muralex.myapp.screens.section

import com.muralex.models.CountryListItem
import com.muralex.myapp.navigation.ScreenNavActions

sealed class SectionUiEvent {
    data class OnItemClicked(val item: CountryListItem) : SectionUiEvent()
}

class SectionEventHandler(
    val navActions: ScreenNavActions
) {
    fun onEvent(event: SectionUiEvent) {
        when (event) {
            is SectionUiEvent.OnItemClicked -> { navActions.toDetailFromList(event.item) }
        }
    }
}

