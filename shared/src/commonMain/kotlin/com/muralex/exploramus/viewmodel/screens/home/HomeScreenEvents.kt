package com.muralex.exploramus.viewmodel.screens.home


import com.muralex.data.repository.functions.getHomeSections
import com.muralex.exploramus.viewmodel.Events

fun Events.retryBootstrapApp() = screenCoroutine {
    val bootstrapResult = stateManager.bootstrapApp()
    if (bootstrapResult.isFailure()) return@screenCoroutine

    val sections = dataRepository.getHomeSections()

    stateManager.updateScreen(HomeScreenState::class) {
        it.copy(
            isLoading = false,
            homeSections = sections.toHomeSectionStates()
        )
    }
}


