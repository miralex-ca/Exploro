package com.exploramus.shared.viewmodel.screens.home


import com.exploramus.data.repository.functions.getHomeSections
import com.exploramus.shared.viewmodel.core.Events

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


