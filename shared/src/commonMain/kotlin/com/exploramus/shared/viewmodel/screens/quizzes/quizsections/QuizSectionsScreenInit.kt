package com.exploramus.shared.viewmodel.screens.quizzes.quizsections

import com.exploramus.data.repository.functions.getAllCountriesCount
import com.exploramus.data.repository.functions.getCountriesCountBySection
import com.exploramus.data.repository.functions.getFavoritesCount
import com.exploramus.data.repository.functions.getQuizCountriesCountAll
import com.exploramus.data.repository.functions.getQuizCountriesCountBySection
import com.exploramus.data.repository.functions.getQuizCountriesCountFavorites
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

        val quizFavoritesCount = dataRepository.getQuizCountriesCountFavorites()
        val quizAllCountriesCount = dataRepository.getQuizCountriesCountAll()

        val continentSections = sections.mapNotNull { section ->
            val totalCount = dataRepository.getCountriesCountBySection(section.id).toInt()
            val eligibleCount = dataRepository.getQuizCountriesCountBySection(section.id)

            if (eligibleCount == 0) return@mapNotNull null

            ContinentSectionState(
                sectionId = section.id,
                sectionName = section.name,
                itemsCount = totalCount
            )
        }

        val quizzesSections = buildList {
            if (quizFavoritesCount > 0) {
                add(QuizSectionState.Favorites(itemsCount = favoritesCount.toInt()))
            }
            if (quizAllCountriesCount > 0) {
                add(QuizSectionState.AllCountries(itemsCount = allCountriesCount.toInt()))
            }

            if (continentSections.isNotEmpty()) {
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