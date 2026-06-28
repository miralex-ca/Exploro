package com.exploramus.shared.viewmodel.screens.search

import com.exploramus.core.models.Country
import com.exploramus.shared.viewmodel.core.ScreenState
import com.exploramus.shared.viewmodel.utils.SingleEffect
import kotlin.uuid.Uuid

data class SearchScreenState(
    val isLoading: Boolean = false,
    val query: String = "",
    val searchResult: SearchResult = SearchResult.Idle,
    val screenBecomeActive: SingleEffect = SingleEffect.idle(),
) : ScreenState


sealed class SearchResult {
    data object Idle : SearchResult()
    data class Success(
        val items: List<SearchListItem>,
        val newVersion: String = Uuid.random().toString()
    ) : SearchResult()

    data object NotFound : SearchResult()
}

data class SearchListItem(
    val id: String,
    val name: String,
    val officialName: String,
    val capital: String,
    val flagImage: String,
)

fun Country.toSearchListItem() = SearchListItem(
    id = id,
    name = name,
    officialName = officialName,
    capital = capital,
    flagImage = flagImage
)

fun List<Country>.toSearchItems() = map { it.toSearchListItem() }

