package com.muralex.data.objects

import com.muralex.models.Country
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryListDto (
    @SerialName("n") val name : String = "",
    @SerialName("p") val population : Int = 0,
) {
    val entity
        get() = Country(
            id = name,
            name = name,
            population = population,
        )
}

fun List<CountryListDto>.entity() = map { it.entity }
