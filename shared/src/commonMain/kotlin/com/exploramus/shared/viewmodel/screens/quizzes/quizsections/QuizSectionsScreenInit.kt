package com.exploramus.shared.viewmodel.screens.quizzes.quizsections

import com.exploramus.data.repository.functions.*
import com.exploramus.shared.viewmodel.core.CallOnInitValues
import com.exploramus.shared.viewmodel.core.ScreenInitSettings
import com.exploramus.shared.viewmodel.core.StateManager


fun StateManager.initQuizSectionsScreen() = ScreenInitSettings(
    title = "Quizzes",
    initState = { _ -> QuizSectionsScreenState(isLoading = true) },
    callOnInit = {
        val favoritesCount = dataRepository.getFavoritesCount()
        val allCountriesCount = dataRepository.getAllCountriesCount()
        val sections = dataRepository.getSections()

        val quizFavoritesCount = dataRepository.getQuizCountriesCountFavorites()
        val quizAllCountriesCount = dataRepository.getQuizCountriesCountAll()

        val continentSections = sections.mapNotNull { section ->
            val totalCount = dataRepository.getCountriesCountBySection(section.id).toInt()
            val quizCountries = dataRepository.getQuizCountriesBySection(section.id)
            val eligibleCount = quizCountries.size

            if (eligibleCount == 0) return@mapNotNull null

            val stats = dataRepository.getSectionStats(quizCountries.map { it.id })
            val masteredCount = stats.mastered

            ContinentSectionState(
                sectionId = section.id,
                sectionName = section.name,
                itemsCount = totalCount,
                eligibleItemsCount = eligibleCount,
                masteredItemsCount = masteredCount,
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