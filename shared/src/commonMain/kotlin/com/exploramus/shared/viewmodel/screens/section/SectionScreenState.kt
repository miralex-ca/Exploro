package com.exploramus.shared.viewmodel.screens.section

import com.exploramus.core.models.Country
import com.exploramus.shared.viewmodel.core.ScreenState

data class SectionScreenState(
    val isLoading: Boolean = false,
    val sectionId: String = "",
    val countries: List<SectionListItem> = emptyList(),
) : ScreenState


data class SectionListItem(
    val id: String,
    val iso2: String,
    val name: String,
    val location: String,
    val flagImage: String,
)

fun Country.toSectionListItem() = SectionListItem(
    id = id,
    iso2 = iso2,
    name = name,
    location = location,
    flagImage = flagImage
)

fun List<Country>.toSectionItems() = map { it.toSectionListItem() }

