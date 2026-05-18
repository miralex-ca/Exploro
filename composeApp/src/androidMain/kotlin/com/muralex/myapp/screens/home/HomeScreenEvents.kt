package com.muralex.myapp.screens.home

import com.muralex.models.CountryListItem
import com.muralex.myapp.navigation.ScreenNavActions

sealed class HomeUiEvent {
    data class OnItemClicked(val item: CountryListItem) : HomeUiEvent()
    data class OnSectionClicked(val section: String) : HomeUiEvent()
}

class HomeEventHandler(
    val navActions: ScreenNavActions
) {
    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.OnItemClicked ->  navActions.toDetailFromList(event.item)
            is HomeUiEvent.OnSectionClicked -> navActions.toSection(event.section)
        }
    }
}

