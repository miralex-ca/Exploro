package com.muralex.exploramus.composables.screens.search

import com.muralex.exploramus.composables.navigation.controller.DetailsNavParams
import com.muralex.exploramus.composables.navigation.controller.ScreenNavActions
import com.muralex.exploramus.viewmodel.core.Events
import com.muralex.exploramus.viewmodel.screens.search.SearchListItem
import com.muralex.exploramus.viewmodel.screens.search.consumeSearchBecomeActiveEffect
import com.muralex.exploramus.viewmodel.screens.search.searchCountriesByQuery

sealed class SearchUiEvent {
    object ConsumeBecomeActive : SearchUiEvent()
    object OnBackClicked : SearchUiEvent()
    data class SearchByQuery(val query: String) : SearchUiEvent()
    data class OnItemClicked(val item: SearchListItem) : SearchUiEvent()
}

class SearchEventHandler(
    val navActions: ScreenNavActions,
    val events: Events,
) {
    fun onEvent(event: SearchUiEvent) {
        when (event) {
            SearchUiEvent.ConsumeBecomeActive -> events.consumeSearchBecomeActiveEffect()
            is SearchUiEvent.OnItemClicked -> navActions.toDetailFromList(event.item.toDetailsNavParams())
            is SearchUiEvent.SearchByQuery -> events.searchCountriesByQuery(event.query)
            SearchUiEvent.OnBackClicked -> navActions.navigateBack()
        }
    }
}

fun SearchListItem.toDetailsNavParams() = DetailsNavParams(id, name)