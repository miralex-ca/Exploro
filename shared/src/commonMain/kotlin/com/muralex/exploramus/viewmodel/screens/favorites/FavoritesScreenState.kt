package com.muralex.exploramus.viewmodel.screens.favorites

import com.muralex.exploramus.viewmodel.core.ScreenState
import com.muralex.models.Country

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
    subregion = subregion,
    flagPngUrl = flagPngUrl
)

fun List<Country>.toFavoriteItems() = map { it.toFavoriteListItem() }