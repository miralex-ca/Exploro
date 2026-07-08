package com.exploramus.shared.viewmodel.screens.quizzes.flashcards

import com.exploramus.data.repository.functions.getAllCountries
import com.exploramus.data.repository.functions.getCountriesBySectionId
import com.exploramus.data.repository.functions.getFavorites
import com.exploramus.shared.viewmodel.core.CallOnInitValues
import com.exploramus.shared.viewmodel.core.ScreenInitSettings
import com.exploramus.shared.viewmodel.core.ScreenParams
import com.exploramus.shared.viewmodel.core.StateManager
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzesSectionType
import kotlinx.serialization.Serializable

@Serializable
data class FlashcardScreenParams(
    val sectionId: String,
    val sectionType: QuizzesSectionType,
) : ScreenParams

fun StateManager.initFlashcardScreen(params: FlashcardScreenParams) = ScreenInitSettings(
    title = "Flashcards",
    initState = { FlashcardScreenState(isLoading = true) },
    callOnInit = {

        val countries = when (params.sectionType) {
            QuizzesSectionType.FAVORITES -> dataRepository.getFavorites()
            QuizzesSectionType.ALL_COUNTRIES -> dataRepository.getAllCountries()
            QuizzesSectionType.CONTINENT -> dataRepository.getCountriesBySectionId(params.sectionId)
        }

        updateScreen(FlashcardScreenState::class) {
            it.copy(
                isLoading = false,
                cards = countries.map { item ->
                    FlashcardState(
                        itemId = item.id,
                        itemName = item.name,
                        officialName = item.officialName,
                        capital = item.capital,
                        flagImage = item.flagImage,
                        region = item.location,
                    )
                },
                currentIndex = 0,
            )
        }
    },
    callOnInitAtEachNavigation = CallOnInitValues.CALL_BEFORE_SHOWING_SCREEN,
)
