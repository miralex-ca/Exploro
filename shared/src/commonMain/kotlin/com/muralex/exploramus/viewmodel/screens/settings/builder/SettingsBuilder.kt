package com.muralex.exploramus.viewmodel.screens.settings.builder

import com.muralex.data.repository.Repository
import kotlin.jvm.JvmName

class SettingsBuilder(repository: Repository) {
    val localSettings = repository.localSettings
    val platformInfo = repository.platformInfo

    @JvmName("buildCategoriesAll")
    fun buildCategories(): List<SettingsCategory> = listOf(
        InterfaceSettingsCategory(localSettings).build(),
        DataSettingsCategory(localSettings).build(),
        InfoSettingsCategory(platformInfo).build(),
    )

    fun buildCategories(blacklistedIds: List<String> = emptyList()): List<SettingsCategory> {
        return listOf(
            InterfaceSettingsCategory(localSettings).build(),
            DataSettingsCategory(localSettings).build(),
            InfoSettingsCategory(platformInfo).build(),
        ).filterSettings(blacklistedIds)
    }
}

@JvmName("filterSettingsExt")
fun List<SettingsCategory>.filterSettings(blacklistedIds: List<String>): List<SettingsCategory> {
    return mapNotNull { category ->
        if (category.id in blacklistedIds) return@mapNotNull null
        val filteredSettings = category.settings.filter { it.key !in blacklistedIds }
        if (filteredSettings.isEmpty()) null
        else category.copy(settings = filteredSettings)
    }
}

fun filterSettings(settings: List<SettingsCategory>, blacklistedIds: List<String>): List<SettingsCategory> {
    return settings.mapNotNull { category ->
        if (category.id in blacklistedIds) return@mapNotNull null
        val filteredSettings = category.settings.filter { it.key !in blacklistedIds }
        if (filteredSettings.isEmpty()) null
        else category.copy(settings = filteredSettings)
    }
}




