package com.muralex.myapp.viewmodel.screens.search

import com.muralex.models.CountryListItem
import com.muralex.myapp.viewmodel.ScreenState
import com.muralex.myapp.viewmodel.utils.SingleEffect

data class SearchScreenState(
    val isLoading: Boolean = false,
    val query: String = "",
    val searchResult: SearchResult = SearchResult.Idle,
    val screenBecomeActive: SingleEffect = SingleEffect.idle(),
) : ScreenState


sealed class SearchResult {
    data object Idle : SearchResult()
    data object Searching : SearchResult()
    data class Success(val items: List<SearchListItem>) : SearchResult()
    data object NotFound : SearchResult()
    data class Error(val message: String) : SearchResult()
}

data class SearchListItem(
    val id: String,
    val name: String,
    val officialName: String,
    val capital: String,
    val flagPngUrl: String,
)

fun CountryListItem.toSearchListItem() = SearchListItem(
    id = id,
    name = name,
    officialName = officialName,
    capital = capital,
    flagPngUrl = flagPngUrl
)

fun List<CountryListItem>.toSearchItems() = map { it.toSearchListItem() }

