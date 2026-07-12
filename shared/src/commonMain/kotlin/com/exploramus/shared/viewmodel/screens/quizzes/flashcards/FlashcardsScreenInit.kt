package com.exploramus.shared.viewmodel.screens.quizzes.flashcards

import com.exploramus.data.repository.functions.getFlashcardConfig
import com.exploramus.data.repository.functions.getFlashcardCountriesAll
import com.exploramus.data.repository.functions.getFlashcardCountriesBySection
import com.exploramus.data.repository.functions.getFlashcardCountriesFavorites
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
    val screenTitle: String,
) : ScreenParams

fun StateManager.initFlashcardScreen(params: FlashcardScreenParams) = ScreenInitSettings(
    title = "Flashcards",
    initState = { FlashcardScreenState(isLoading = true) },
    callOnInit = {

        val countries = when (params.sectionType) {
            QuizzesSectionType.FAVORITES -> dataRepository.getFlashcardCountriesFavorites()
            QuizzesSectionType.ALL_COUNTRIES -> dataRepository.getFlashcardCountriesAll()
            QuizzesSectionType.CONTINENT -> dataRepository.getFlashcardCountriesBySection(params.sectionId)
        }

        val config = dataRepository.getFlashcardConfig()
        val flashcards = countries.map { it.toFlashcardState() }

        updateScreen(FlashcardScreenState::class) {
            it.copy(
                isLoading = false,
                screenTitle = params.screenTitle,
                deck = FlashcardDeckState(
                    config = config,
                    cards = if (config.shuffleEnabled) flashcards.shuffled() else flashcards,
                ),
                originalCards = flashcards,
            )
        }
    },
    callOnInitAtEachNavigation = CallOnInitValues.DONT_CALL,
    clearStateCacheWhenScreenIsRemovedFromBackstack = true,
)
