package com.muralex.data.functions

import com.muralex.data.Repository
import com.muralex.models.ThemeMode

fun Repository.getThemeMode(): ThemeMode {
    return themeModeFromIndex(localSettings.themeModeId)
}

fun Repository.saveThemeMode(mode: ThemeMode) {
    localSettings.themeModeId = themeModeToIndex(mode)
}

fun themeModeFromIndex(index: Int): ThemeMode =
    when (index) {
        0 -> ThemeMode.LIGHT
        1 -> ThemeMode.DARK
        else -> ThemeMode.SYSTEM
    }

fun themeModeToIndex(mode: ThemeMode): Int =
    when (mode) {
        ThemeMode.LIGHT -> 0
        ThemeMode.DARK -> 1
        ThemeMode.SYSTEM -> 2
    }