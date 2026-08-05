package com.exploramus.shared.viewmodel.screens.quizzes.groupeditems

import com.exploramus.core.models.QuizItemStatus
import com.exploramus.core.models.isValidForQuiz
import com.exploramus.data.repository.functions.getAllCountries
import com.exploramus.data.repository.functions.getCountriesBySectionId
import com.exploramus.data.repository.functions.getFavorites
import com.exploramus.data.repository.functions.getQuizItemResults
import com.exploramus.shared.viewmodel.core.CallOnInitValues
import com.exploramus.shared.viewmodel.core.ScreenInitSettings
import com.exploramus.shared.viewmodel.core.ScreenParams
import com.exploramus.shared.viewmodel.core.StateManager
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzesSectionType
import kotlinx.serialization.Serializable

@Serializable
data class GroupedItemsScreenParams(
    val sectionId: String,
    val sectionType: QuizzesSectionType,
    val masteryStatus: QuizItemStatus? = null,
    val screenTitle: String? = null
) : ScreenParams

fun StateManager.initGroupedItemsScreen(params: GroupedItemsScreenParams) = ScreenInitSettings(
    title = params.screenTitle ?: "Items",
    initState = {
        GroupedItemsScreenState(
            isLoading = true,
            title = params.screenTitle ?: "",
            masteryStatus = params.masteryStatus
        )
    },
    callOnInit = {
        val countries = when (params.sectionType) {
            QuizzesSectionType.FAVORITES -> dataRepository.getFavorites()
            QuizzesSectionType.ALL_COUNTRIES -> dataRepository.getAllCountries()
            QuizzesSectionType.CONTINENT -> dataRepository.getCountriesBySectionId(params.sectionId)
        }.filter { it.isValidForQuiz }

        val results = dataRepository.getQuizItemResults(countries.map { it.id }).associateBy { it.id }

        val items = countries.map { country ->
            val result = results[country.id]
            val status = result?.status ?: QuizItemStatus.UNKNOWN
            MasteryItemState(
                id = country.id,
                iso2 = country.iso2,
                name = country.name,
                flagEmoji = country.flagEmoji,
                flagImage = country.flagImage,
                subregion = country.location,
                status = status,
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
    },
    callOnInitAtEachNavigation = CallOnInitValues.CALL_BEFORE_SHOWING_SCREEN
)
