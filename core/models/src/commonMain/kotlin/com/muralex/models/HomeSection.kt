package com.muralex.models

data class HomeSection(
    val sectionId: String,
    val sectionName: String,
    val countries: List<Country>
)