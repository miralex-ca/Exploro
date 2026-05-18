package com.muralex.myapp.viewmodel.appconfig

import com.muralex.data.functions.getThemeIndex
import com.muralex.myapp.viewmodel.StateManager


data class AppConfig(
    val themeMode: ThemeStatus = ThemeStatus.SYSTEM
)

fun StateManager.updateAppConfig() {
    val themeIndex = dataRepository.getThemeIndex()
    val themeStatus = themeStausByIndex(themeIndex)

    val appConfig = AppConfig(
        themeMode = themeStatus
    )

    updateAppConfigState(appConfig)
}


enum class ThemeStatus {
    LIGHT,
    DARK,
    SYSTEM
}

fun themeStausByIndex(index: Int) : ThemeStatus {
    return when (index) {
        0 -> ThemeStatus.LIGHT
        1 -> ThemeStatus.DARK
        else -> ThemeStatus.SYSTEM
    }
}

