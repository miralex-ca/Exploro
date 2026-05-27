package com.muralex.exploramus.viewmodel.screens.search

import com.muralex.data.repository.functions.searchCountries
import com.muralex.exploramus.viewmodel.Events


fun Events.searchCountriesByQuery(query: String) = screenCoroutine {
    val results = dataRepository.searchCountries(query).toSearchItems()

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