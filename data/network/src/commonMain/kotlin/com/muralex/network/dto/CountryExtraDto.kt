package com.muralex.network.dto

import com.muralex.models.CountryExtraInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryExtraDto (
    @SerialName("v") val vaccines : String = "",
) {
    val vaccinesList : List<String>
        get() = vaccines.split(", ")

    val entity
        get() = CountryExtraInfo(
            vaccines = vaccines
        )

}