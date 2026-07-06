package com.exploramus.shared.viewmodel.screens.quizzes.quizsections

import com.exploramus.data.repository.functions.getAllCountriesCount
import com.exploramus.data.repository.functions.getCountriesCountBySection
import com.exploramus.data.repository.functions.getFavoritesCount
import com.exploramus.data.repository.functions.getSections
import com.exploramus.shared.viewmodel.core.CallOnInitValues
import com.exploramus.shared.viewmodel.core.ScreenInitSettings
import com.exploramus.shared.viewmodel.core.StateManager


fun StateManager.initQuizSectionsScreen() = ScreenInitSettings(
    title = "Quizzes",
    initState = { QuizSectionsScreenState(isLoading = true) },
    callOnInit = {
        val favoritesCount = dataRepository.getFavoritesCount()
        val allCountriesCount = dataRepository.getAllCountriesCount()
        val sections = dataRepository.getSections()

        val continentSections = sections.map { section ->
            ContinentSectionState(
                sectionId = section.id,
                sectionName = section.name,
                itemsCount = dataRepository.getCountriesCountBySection(section.id).toInt()
            )
        }

        val quizzesSections = buildList {
            if (favoritesCount > 0) {
                add(QuizSectionState.Favorites(itemsCount = favoritesCount.toInt()))
            }
            add(QuizSectionState.AllCountries(itemsCount = allCountriesCount.toInt()))

            if (continentSections .isNotEmpty()) {
                add(QuizSectionState.Continents(continents = continentSections))
            }
        }

        updateScreen(QuizSectionsScreenState::class) {
            it.copy(
                isLoading = false,
                quizzesSections = quizzesSections
            )
        }
    },
    callOnInitAtEachNavigation = CallOnInitValues.CALL_BEFORE_SHOWING_SCREEN
)