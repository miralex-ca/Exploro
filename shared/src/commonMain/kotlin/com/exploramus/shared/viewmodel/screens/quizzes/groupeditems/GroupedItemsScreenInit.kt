package com.exploramus.shared.viewmodel.screens.quizzes.groupeditems

import com.exploramus.core.models.QuizItemStatus
import com.exploramus.data.repository.functions.getAllCountries
import com.exploramus.data.repository.functions.getCountriesBySectionId
import com.exploramus.data.repository.functions.getQuizItemResults
import com.exploramus.shared.viewmodel.core.GroupedItemsScreenParams
import com.exploramus.shared.viewmodel.core.ScreenInitSettings
import com.exploramus.shared.viewmodel.core.StateManager
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzesSectionType

fun StateManager.initGroupedItemsScreen(params: GroupedItemsScreenParams) = ScreenInitSettings(
    title = params.screenTitle ?: "",
    initState = { _ -> GroupedItemsScreenState(isLoading = true, title = params.screenTitle ?: "", masteryStatus = params.masteryStatus) },
    callOnInit = {
        val countries = when (params.sectionType) {
            QuizzesSectionType.ALL_COUNTRIES -> dataRepository.getAllCountries()
            QuizzesSectionType.CONTINENT -> dataRepository.getCountriesBySectionId(params.sectionId)
            QuizzesSectionType.FAVORITES -> emptyList()
        }

        val results = dataRepository.getQuizItemResults(countries.map { it.id }).associateBy { it.id }

        val items = countries.map { country ->
            val result = results[country.id]
            MasteryItemState(
                id = country.id,
                iso2 = country.iso2,
                name = country.name,
                flagEmoji = country.flagEmoji,
                flagImage = country.flagImage,
                subregion = country.location,
                status = result?.status ?: QuizItemStatus.UNKNOWN,
                errorCount = result?.errors ?: 0
            )
        }.filter {
            params.masteryStatus == null || it.status == params.masteryStatus
        }

        updateScreen(GroupedItemsScreenState::class) {
            it.copy(
                isLoading = false,
                items = items
            )
        }
    }
)
