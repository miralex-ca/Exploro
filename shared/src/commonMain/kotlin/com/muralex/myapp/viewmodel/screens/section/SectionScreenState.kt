package com.muralex.myapp.viewmodel.screens.section

import com.muralex.models.CountryListItem
import com.muralex.myapp.viewmodel.ScreenState

data class SectionScreenState(
    val isLoading: Boolean = false,
    val countries: List<SectionListItem> = emptyList(),
) : ScreenState


data class SectionListItem(
    val id: String,
    val name: String,
    val subregion: String,
    val flagPngUrl: String,
)

fun CountryListItem.toSectionListItem() = SectionListItem(
    id = id,
    name = name,
    subregion = subregion,
    flagPngUrl = flagPngUrl
)

fun List<CountryListItem>.toSectionItems() = map { it.toSectionListItem() }

