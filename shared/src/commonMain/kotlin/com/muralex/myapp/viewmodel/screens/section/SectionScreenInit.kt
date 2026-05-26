package com.muralex.myapp.viewmodel.screens.section

import com.muralex.data.repository.functions.getCountriesBySectionId
import com.muralex.myapp.viewmodel.ScreenParams
import com.muralex.myapp.viewmodel.StateManager
import com.muralex.myapp.viewmodel.screens.CallOnInitValues
import com.muralex.myapp.viewmodel.screens.ScreenInitSettings
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