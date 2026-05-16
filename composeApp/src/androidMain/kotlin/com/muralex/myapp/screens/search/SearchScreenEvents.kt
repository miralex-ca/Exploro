package com.muralex.myapp.screens.search

import com.muralex.models.CountryListItem
import com.muralex.myapp.navigation.ScreenNavigator
import com.muralex.myapp.viewmodel.Events
import com.muralex.myapp.viewmodel.screens.Screen
import com.muralex.myapp.viewmodel.screens.countrydetail.CountryDetailParams
import com.muralex.myapp.viewmodel.screens.search.consumeSearchBecomeActiveEffect
import com.muralex.myapp.viewmodel.screens.search.searchCountriesByQuery

sealed class SearchScreenUiEvent {
    object DidBecomeActive : SearchScreenUiEvent()
    data class SearchByQuery(val query: String) : SearchScreenUiEvent()
    data class OnItemClicked(val item: CountryListItem, val navigator: ScreenNavigator) : SearchScreenUiEvent()
}

fun Events.onSearchScreenEvent(event: SearchScreenUiEvent) {
    when (event) {
        SearchScreenUiEvent.DidBecomeActive -> consumeSearchBecomeActiveEffect()
        is SearchScreenUiEvent.SearchByQuery -> searchCountriesByQuery(event.query)
        is SearchScreenUiEvent.OnItemClicked -> {
            event.navigator.navigate(
                screen = Screen.CountryDetail,
                screenParams = CountryDetailParams(
                    countryName = event.item.name,
                    countryCode = event.item.id
                )
            )
        }

    }
}