package com.exploramus.shared.viewmodel.screens.favorites

import com.exploramus.core.models.Country
import com.exploramus.shared.viewmodel.core.ScreenState

data class FavoritesScreenState(
    val isLoading: Boolean = false,
    val favorites: List<FavoriteListItem> = emptyList(),
) : ScreenState


data class FavoriteListItem(
    val id: String,
    val iso2: String,
    val name: String,
    val location: String,
    val flagImage: String,
)

fun Country.toFavoriteListItem() = FavoriteListItem(
    id = id,
    iso2 = iso2,
    name = name,
    location = location,
    flagImage = flagImage
)

fun List<Country>.toFavoriteItems() = map { it.toFavoriteListItem() }