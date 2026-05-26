package com.muralex.myapp.viewmodel.screens.home

import com.muralex.models.CountryListItem
import com.muralex.models.HomeSection
import com.muralex.myapp.viewmodel.ScreenState

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

fun CountryListItem.toHomeListItem() = HomeListItem(
    id = id,
    name = name,
    flagPngUrl = flagPngUrl
)

fun List<CountryListItem>.toHomeItems() = map { it.toHomeListItem() }


