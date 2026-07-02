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
    val currentIndex: Int = 0,
    val pagerController: DetailsPagerController = DetailsPagerController(),
): ScreenState


class DetailsPagerController(
    initialIndex: Int = 0,
    val totalCount: Int = 0,
) {
    var currentIndex: Int = initialIndex
        private set

    val hasPrevious: Boolean get() = currentIndex > 0
    val hasNext: Boolean get() = currentIndex < totalCount - 1

    fun goToNext(): Int {
        if (hasNext) currentIndex++
        return currentIndex
    }

    fun goToPrevious(): Int {
        if (hasPrevious) currentIndex--
        return currentIndex
    }

    fun goToPage(index: Int): Int {
        currentIndex = index.coerceIn(0, totalCount - 1)
        return currentIndex
    }
}