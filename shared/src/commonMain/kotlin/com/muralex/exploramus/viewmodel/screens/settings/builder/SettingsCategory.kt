package com.muralex.exploramus.viewmodel.screens.settings.builder

import com.muralex.exploramus.viewmodel.resources.StringRef

data class SettingsCategory(
    val id: String,
    val title: StringRef? = null,
    val settings: List<Setting>
)

fun List<SettingsCategory>.updateSetting(key: String, update: (Setting) -> Setting): List<SettingsCategory> {
    return map { category ->
        category.copy(
            settings = category.settings.map { setting ->
                if (setting.key == key) update(setting) else setting
            }
        )
    }
}