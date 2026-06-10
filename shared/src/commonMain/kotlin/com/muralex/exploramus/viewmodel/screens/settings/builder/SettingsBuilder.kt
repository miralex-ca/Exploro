package com.muralex.exploramus.viewmodel.screens.settings.builder

import com.muralex.data.repository.Repository

class SettingsBuilder(repository: Repository) {
    val localSettings = repository.localSettings
    val platformInfo = repository.platformInfo

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
        ).filter(blacklistedIds)
    }
}

fun List<SettingsCategory>.filter(blacklistedIds: List<String>): List<SettingsCategory> {
    return mapNotNull { category ->
        if (category.id in blacklistedIds) return@mapNotNull null
        val filteredSettings = category.settings.filter { it.key !in blacklistedIds }
        if (filteredSettings.isEmpty()) null
        else category.copy(settings = filteredSettings)
    }
}






