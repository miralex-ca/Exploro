package com.exploramus.shared.viewmodel.screens.details.singledetail

import com.exploramus.shared.viewmodel.core.ScreenState
import com.exploramus.shared.viewmodel.screens.details.CountryDetailsState

data class DetailsScreenState (
    val isLoading: Boolean = false,
    val screenTitle: String = "",
    val details: CountryDetailsState? = null,
    val isFavorite: Boolean = false
): ScreenState


