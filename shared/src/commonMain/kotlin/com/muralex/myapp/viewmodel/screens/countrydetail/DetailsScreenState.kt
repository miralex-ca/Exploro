package com.muralex.myapp.viewmodel.screens.countrydetail

import com.muralex.models.CountryFull
import com.muralex.myapp.viewmodel.ScreenState

data class DetailsScreenState (
    val isLoading: Boolean = false,
    val countryDetails : CountryFull? = null,
    val isFavorite: Boolean = false,
): ScreenState