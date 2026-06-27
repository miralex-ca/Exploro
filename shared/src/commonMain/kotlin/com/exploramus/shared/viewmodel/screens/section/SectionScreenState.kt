package com.exploramus.shared.viewmodel.screens.section

import com.exploramus.core.models.Country
import com.exploramus.shared.viewmodel.core.ScreenState

data class SectionScreenState(
    val isLoading: Boolean = false,
    val countries: List<SectionListItem> = emptyList(),
) : ScreenState


data class SectionListItem(
    val id: String,
    val name: String,
    val location: String,
    val flagImage: String,
)

fun Country.toSectionListItem() = SectionListItem(
    id = id,
    name = name,
    location = location,
    flagImage = flagImage
)

fun List<Country>.toSectionItems() = map { it.toSectionListItem() }

