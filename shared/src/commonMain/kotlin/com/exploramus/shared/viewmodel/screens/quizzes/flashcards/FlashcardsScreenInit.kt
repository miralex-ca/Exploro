package com.exploramus.shared.viewmodel.screens.quizzes.flashcards

import com.exploramus.data.repository.functions.getFlashcardConfig
import com.exploramus.data.repository.functions.getQuizCountriesAll
import com.exploramus.data.repository.functions.getQuizCountriesBySection
import com.exploramus.data.repository.functions.getQuizCountriesFavorites
import com.exploramus.shared.viewmodel.core.CallOnInitValues
import com.exploramus.shared.viewmodel.core.FlashcardScreenParams
import com.exploramus.shared.viewmodel.core.ScreenInitSettings
import com.exploramus.shared.viewmodel.core.StateManager
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzesSectionType

fun StateManager.initFlashcardScreen(params: FlashcardScreenParams) = ScreenInitSettings(
    title = "Flashcards",
    initState = { _ -> FlashcardScreenState(isLoading = true) },
    callOnInit = {

        val countries = when (params.sectionType) {
            QuizzesSectionType.FAVORITES -> dataRepository.getQuizCountriesFavorites()
            QuizzesSectionType.ALL_COUNTRIES -> dataRepository.getQuizCountriesAll()
            QuizzesSectionType.CONTINENT -> dataRepository.getQuizCountriesBySection(params.sectionId)
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
