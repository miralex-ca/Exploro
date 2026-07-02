package com.exploramus.shared.viewmodel.screens.details.detailpager

import com.exploramus.shared.viewmodel.core.ScreenState
import com.exploramus.shared.viewmodel.screens.details.CountryDetailsState

data class DetailsPagerScreenState (
    val isLoading: Boolean = false,
    val resetKey: String = "",
    val initialIndex: Int = 0,
    val detailsList:  List<CountryDetailsState> = emptyList(),
): ScreenState