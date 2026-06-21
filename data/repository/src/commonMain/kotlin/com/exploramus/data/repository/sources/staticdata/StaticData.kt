package com.exploramus.data.repository.sources.staticdata

val homeSections = listOf(
    HomeSectionData.from("Europe"),
    HomeSectionData.from("Asia"),
    HomeSectionData.from("Africa"),
    HomeSectionData.from("North America"),
    HomeSectionData.from("South America"),
    HomeSectionData.from("Oceania")
)

data class HomeSectionData(
    val key: String,
    val name: String
) {
    companion object {
        fun from(name: String) = HomeSectionData(key = name, name = name)
    }
}