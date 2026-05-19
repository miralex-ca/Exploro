package com.muralex.myapp.viewmodel.screens.settings


import com.muralex.data.repository.functions.getThemeMode
import com.muralex.data.repository.functions.saveThemeMode
import com.muralex.models.ThemeMode
import com.muralex.myapp.viewmodel.Events


fun Events.saveThemeMode(themeMode: ThemeMode) = screenCoroutine {
    dataRepository.saveThemeMode(themeMode)
    val current = dataRepository.getThemeMode()

    stateManager.updateScreen(SettingsScreenState::class) {
        it.copy(
            savedThemeMode = current
        )
    }

    stateManager.updateAppEnvironment(
        state = stateManager.appEnvironment.value.copy(
            themeMode = current
        )
    )
}