package com.exploramus.shared.viewmodel.screens.details.detailpager

import com.exploramus.core.models.Country
import com.exploramus.shared.viewmodel.core.ScreenState
import com.exploramus.shared.viewmodel.screens.details.CountryDetailsState

data class DetailsPagerScreenState (
    val isLoading: Boolean = false,
    val countries: List<Country> = emptyList(),
    val details: CountryDetailsState? = null,
    val isFavorite: Boolean = false,
    val hasPrevious: Boolean = false,
    val hasNext: Boolean = false,
): ScreenState
