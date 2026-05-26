package com.muralex.myapp.viewmodel.screens.settings.builder

import com.muralex.data.repository.Repository

class SettingsBuilder(repository: Repository) {
    val localSettings = repository.localSettings
    val platformInfo = repository.platformInfo

    fun buildCategories(): List<SettingsCategory> = listOf(
        InterfaceSettingsCategory(localSettings).build(),
        DataSettingsCategory(localSettings).build(),
        InfoSettingsCategory(platformInfo).build(),
    )
}






