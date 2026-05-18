package com.muralex.myapp.screens.search

import com.muralex.models.CountryListItem
import com.muralex.myapp.navigation.ScreenNavActions
import com.muralex.myapp.viewmodel.Events
import com.muralex.myapp.viewmodel.screens.search.consumeSearchBecomeActiveEffect
import com.muralex.myapp.viewmodel.screens.search.searchCountriesByQuery

sealed class SearchUiEvent {
    object DidBecomeActive : SearchUiEvent()
    object OnBackClicked : SearchUiEvent()
    data class SearchByQuery(val query: String) : SearchUiEvent()
    data class OnItemClicked(val item: CountryListItem) : SearchUiEvent()
}

class SearchEventHandler(
    val navActions: ScreenNavActions,
    val events: Events,
) {
    fun onEvent(event: SearchUiEvent) {
        when (event) {
            SearchUiEvent.DidBecomeActive -> events.consumeSearchBecomeActiveEffect()
            is SearchUiEvent.OnItemClicked -> navActions.toDetailFromList(event.item)
            is SearchUiEvent.SearchByQuery -> events.searchCountriesByQuery(event.query)
            SearchUiEvent.OnBackClicked -> navActions.navigateBack()
        }
    }
}