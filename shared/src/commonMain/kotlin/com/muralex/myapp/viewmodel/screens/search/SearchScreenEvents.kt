package com.muralex.myapp.viewmodel.screens.search

import com.muralex.data.functions.searchCountries
import com.muralex.models.CountryListItem
import com.muralex.myapp.viewmodel.Events
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay


fun Events.searchCountriesByQuery(query: String) = screenCoroutine {
    val results = dataRepository.searchCountries(query)

    stateManager.updateScreen(SearchScreenState::class) {
        it.copy(
            searchResult = if (results.isEmpty())
                SearchResult.NotFound
            else
                SearchResult.Success(results)
        )
    }
}

fun Events.consumeSearchBecomeActiveEffect() = screenCoroutine {
    stateManager.updateScreen(SearchScreenState::class) {
        it.copy(
            screenBecomeActive = it.screenBecomeActive.consume()
        )
    }
}