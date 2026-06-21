package com.exploramus.shared.viewmodel.screens.favorites

import com.exploramus.shared.viewmodel.core.ScreenState
import com.exploramus.shared.viewmodel.screens.countrydetail.CountryDetailsState
import com.exploramus.core.models.Country

data class FavoritesScreenState (
    val isLoading : Boolean = false,
    val favorites : List<FavoriteListItem> = emptyList(),
    val selectedCountry: CountryDetailsState? = null,
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