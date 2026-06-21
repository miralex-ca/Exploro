package com.exploramus.shared.viewmodel.screens.home

import com.exploramus.shared.viewmodel.core.ScreenState
import com.exploramus.core.models.Country
import com.exploramus.core.models.HomeSection

data class HomeScreenState(
    val isLoading: Boolean = false,
    val homeSections: List<HomeSectionState> = emptyList(),
) : ScreenState

data class HomeSectionState(
    val sectionId: String,
    val sectionName: String,
    val sectionListItems: List<HomeListItem>
)

fun HomeSection.toHomeSectionState() = HomeSectionState(
    sectionId = sectionId,
    sectionName = sectionName,
    sectionListItems = countries.toHomeItems()
)

fun List<HomeSection>.toHomeSectionStates() = map { it.toHomeSectionState() }

data class HomeListItem(
    val id: String,
    val name: String,
    val flagPngUrl: String,
)

fun Country.toHomeListItem() = HomeListItem(
    id = id,
    name = name,
    flagPngUrl = flagPngUrl
)

fun List<Country>.toHomeItems() = map { it.toHomeListItem() }


