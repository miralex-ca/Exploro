package com.muralex.data.functions

import com.muralex.data.Repository


suspend fun Repository.getThemeModeIndex(): Int = withRepoContext {
    localSettings.themeModeId
}

suspend fun Repository.setThemeModeByIndex(index: Int) = withRepoContext {
    localSettings.themeModeId = index
}

fun Repository.getThemeIndex(): Int {
   return localSettings.themeModeId
}