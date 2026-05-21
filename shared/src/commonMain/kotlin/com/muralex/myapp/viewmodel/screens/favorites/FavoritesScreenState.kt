package com.muralex.myapp.viewmodel.screens.favorites

import com.muralex.models.CountryListItem
import com.muralex.myapp.viewmodel.ScreenState

data class FavoritesScreenState (
    val isLoading : Boolean = false,
    val favorites : List<CountryListItem> = emptyList(),
): ScreenState