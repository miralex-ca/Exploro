package com.muralex.myapp.viewmodel.screens.settings

import com.muralex.data.functions.getThemeIndex
import com.muralex.data.functions.getThemeModeIndex
import com.muralex.data.functions.setThemeModeByIndex
import com.muralex.myapp.viewmodel.Events
import com.muralex.myapp.viewmodel.appconfig.themeStausByIndex

fun Events.setThemeModeByIndex(index: Int) = screenCoroutine {

    println("setThemeModeByIndex: $index")

    dataRepository.setThemeModeByIndex(index)

    val current = dataRepository.getThemeModeIndex()

    println("setThemeModeByIndex: $index")

    stateManager.updateScreen(SettingsScreenState::class) {
        it.copy(
            savedThemeMode = current
        )
    }

    stateManager.updateAppConfigState(
        config = stateManager.appConfig.value.copy(
            themeMode = themeStausByIndex(current)
        )
    )
}

fun Events.getThemeModeIndex() : Int {
    return dataRepository.getThemeIndex().also {
        navigation.updateNavigationState()
    }
}