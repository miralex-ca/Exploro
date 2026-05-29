package com.muralex.exploramus.viewmodel.screens.section

import com.muralex.exploramus.viewmodel.core.ScreenState
import com.muralex.models.Country

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

fun Country.toSectionListItem() = SectionListItem(
    id = id,
    name = name,
    subregion = subregion,
    flagPngUrl = flagPngUrl
)

fun List<Country>.toSectionItems() = map { it.toSectionListItem() }

