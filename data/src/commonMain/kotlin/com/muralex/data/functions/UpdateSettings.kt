package com.muralex.data.functions

import com.muralex.data.Repository
import com.muralex.models.ThemeMode

fun Repository.getThemeMode(): ThemeMode {
    return ThemeMode.fromId(localSettings.themeModeId)
}

fun Repository.saveThemeMode(themeMode: ThemeMode) {
    localSettings.themeModeId = themeMode.id
}