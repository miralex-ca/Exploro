package com.muralex.myapp.screens.home

import com.muralex.models.CountryListItem
import com.muralex.myapp.navigation.ScreenNavigator
import com.muralex.myapp.navigation.toDetailFromList
import com.muralex.myapp.navigation.toSection

sealed class HomeUiEvent {
    data class OnItemClicked(val item: CountryListItem) : HomeUiEvent()
    data class OnSectionClicked(val section: String) : HomeUiEvent()
}

class HomeEventHandler(
    val navigator: ScreenNavigator
) {
    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.OnItemClicked ->  navigator.toDetailFromList(event.item)
            is HomeUiEvent.OnSectionClicked -> navigator.toSection(event.section)
        }
    }
}

