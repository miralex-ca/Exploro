package com.muralex.exploramus.viewmodel.screens.search

import com.muralex.exploramus.viewmodel.core.ScreenState
import com.muralex.exploramus.viewmodel.utils.SingleEffect
import com.muralex.models.Country

data class SearchScreenState(
    val isLoading: Boolean = false,
    val query: String = "",
    val searchResult: SearchResult = SearchResult.Idle,
    val screenBecomeActive: SingleEffect = SingleEffect.idle(),
) : ScreenState


sealed class SearchResult {
    data object Idle : SearchResult()
    data class Success(val items: List<SearchListItem>) : SearchResult()
    data object NotFound : SearchResult()
}

data class SearchListItem(
    val id: String,
    val name: String,
    val officialName: String,
    val capital: String,
    val flagPngUrl: String,
)

fun Country.toSearchListItem() = SearchListItem(
    id = id,
    name = name,
    officialName = officialName,
    capital = capital,
    flagPngUrl = flagPngUrl
)

fun List<Country>.toSearchItems() = map { it.toSearchListItem() }

