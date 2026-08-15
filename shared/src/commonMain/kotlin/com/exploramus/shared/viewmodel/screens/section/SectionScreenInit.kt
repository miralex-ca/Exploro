package com.exploramus.shared.viewmodel.screens.section

import com.exploramus.data.repository.functions.getCountriesBySectionId
import com.exploramus.shared.viewmodel.core.CallOnInitValues
import com.exploramus.shared.viewmodel.core.ScreenInitSettings
import com.exploramus.shared.viewmodel.core.SectionParams
import com.exploramus.shared.viewmodel.core.StateManager

fun StateManager.initSectionScreen(params: SectionParams) = ScreenInitSettings(
    title = params.screenTitle ?: "",
    initState = { _ -> SectionScreenState(isLoading = true) },
    callOnInit = {
        val sectionId = params.continent
        val countries = dataRepository.getCountriesBySectionId(params.continent)

        updateScreen(SectionScreenState::class) {
            it.copy(
                isLoading = false,
                sectionId = sectionId,
                countries = countries.toSectionItems()
            )
        }
    },
    callOnInitAtEachNavigation = CallOnInitValues.DONT_CALL
)
