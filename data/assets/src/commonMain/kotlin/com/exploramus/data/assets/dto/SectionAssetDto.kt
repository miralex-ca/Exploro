package com.exploramus.data.assets.dto

import com.exploramus.core.models.Section
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SectionAssetDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String
)

internal fun SectionAssetDto.toSection() = Section(
    id = id,
    name = name
)
