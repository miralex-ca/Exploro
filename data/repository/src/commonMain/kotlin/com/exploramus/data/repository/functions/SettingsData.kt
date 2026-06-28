package com.exploramus.data.repository.functions

import com.exploramus.core.models.ThemeMode
import com.exploramus.data.repository.Repository

fun Repository.getThemeMode(): ThemeMode {
    return ThemeMode.fromId(localSettings.themeModeId)
}

fun Repository.saveThemeMode(themeMode: ThemeMode) {
    localSettings.themeModeId = themeMode.id
}

fun Repository.setFavoriteSwipeEnabled(enabled: Boolean) {
    localSettings.favoriteSwipeEnabled = enabled
}

fun Repository.getFavoriteSwipeEnabled(): Boolean {
    return localSettings.favoriteSwipeEnabled
}
