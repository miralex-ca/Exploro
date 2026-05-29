package com.muralex.exploramus.viewmodel.screens.section

import com.muralex.data.repository.functions.getCountriesBySectionId
import com.muralex.exploramus.viewmodel.core.ScreenParams
import com.muralex.exploramus.viewmodel.core.StateManager
import com.muralex.exploramus.viewmodel.screens.CallOnInitValues
import com.muralex.exploramus.viewmodel.screens.ScreenInitSettings
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