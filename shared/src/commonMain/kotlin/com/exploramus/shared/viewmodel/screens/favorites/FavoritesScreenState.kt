package com.exploramus.shared.viewmodel.screens.favorites

import com.exploramus.core.models.Country
import com.exploramus.shared.viewmodel.core.ScreenState

data class FavoritesScreenState (
    val isLoading : Boolean = false,
    val favorites : List<FavoriteListItem> = emptyList(),
): ScreenState


data class FavoriteListItem(
    val id: String,
    val name: String,
    val subregion: String,
    val flagPngUrl: String,
)

fun Country.toFavoriteListItem() = FavoriteListItem(
    id = id,
    name = name,
    subregion = location,
    flagPngUrl = flagImage
)

fun List<Country>.toFavoriteItems() = map { it.toFavoriteListItem() }