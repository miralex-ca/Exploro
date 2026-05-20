package com.muralex.myapp.viewmodel.appenvironment

import com.muralex.data.repository.functions.getThemeMode
import com.muralex.models.ThemeMode
import com.muralex.myapp.viewmodel.StateManager

data class AppEnvironment(
    val themeMode: ThemeMode = ThemeMode.DEFAULT
)

fun StateManager.prepareAppEnvironment() {
    val appEnvironment = AppEnvironment(
        themeMode =  dataRepository.getThemeMode()
    )

    updateAppEnvironment(appEnvironment)
}

