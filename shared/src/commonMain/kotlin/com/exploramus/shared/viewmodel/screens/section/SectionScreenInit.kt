package com.exploramus.shared.viewmodel.screens.section

import com.exploramus.data.repository.functions.getCountriesBySectionId
import com.exploramus.shared.viewmodel.core.CallOnInitValues
import com.exploramus.shared.viewmodel.core.ScreenInitSettings
import com.exploramus.shared.viewmodel.core.ScreenParams
import com.exploramus.shared.viewmodel.core.StateManager
import kotlinx.serialization.Serializable

@Serializable
data class SectionParams(val continent: String, val screenTitle: String? = null) : ScreenParams

fun StateManager.initSectionScreen(params: SectionParams) = ScreenInitSettings(
    title = params.screenTitle ?: "",
    initState = { SectionScreenState(isLoading = true) },
    callOnInit = {
        val countries = dataRepository.getCountriesBySectionId(params.continent)

        updateScreen(SectionScreenState::class) {
            it.copy(
                isLoading = false,
                countries = countries.toSectionItems()
            )
        }
    },
    callOnInitAtEachNavigation = CallOnInitValues.DONT_CALL
)