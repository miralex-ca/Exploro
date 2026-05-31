package com.muralex.exploramus.viewmodel.screens.search

import com.muralex.data.repository.functions.searchCountries
import com.muralex.exploramus.viewmodel.core.Events


fun Events.searchCountriesByQuery(query: String) = screenCoroutine {
    val results = dataRepository.searchCountries(query).toSearchItems()

    val searchResult = when {
        results.isNotEmpty() -> SearchResult.Success(results)
        query.isNotBlank() && results.isEmpty() -> SearchResult.NotFound
        else -> SearchResult.Idle
    }

    stateManager.updateScreen(SearchScreenState::class) {
        it.copy(
            searchResult = searchResult
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