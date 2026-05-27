package com.muralex.exploramus.viewmodel.screens.favorites

import com.muralex.models.CountryListItem
import com.muralex.exploramus.viewmodel.ScreenState

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

fun CountryListItem.toFavoriteListItem() = FavoriteListItem(
    id = id,
    name = name,
    subregion = subregion,
    flagPngUrl = flagPngUrl
)

fun List<CountryListItem>.toFavoriteItems() = map { it.toFavoriteListItem() }