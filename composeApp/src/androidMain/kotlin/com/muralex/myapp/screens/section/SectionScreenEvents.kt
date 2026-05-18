package com.muralex.myapp.screens.section

import com.muralex.models.CountryListItem
import com.muralex.myapp.navigation.ScreenNavigator
import com.muralex.myapp.navigation.toDetailFromList

sealed class SectionUiEvent {
    data class OnItemClicked(val item: CountryListItem) : SectionUiEvent()
}

class SectionEventHandler(
    val navigator: ScreenNavigator
) {
    fun onEvent(event: SectionUiEvent) {
        when (event) {
            is SectionUiEvent.OnItemClicked -> { navigator.toDetailFromList(event.item) }
        }
    }
}

