package com.exploramus.shared.viewmodel.screens

import com.exploramus.shared.viewmodel.core.ScreenIdentifier
import com.exploramus.shared.viewmodel.core.ScreenInitSettings
import com.exploramus.shared.viewmodel.core.StateManager
import com.exploramus.shared.viewmodel.screens.details.detailpager.initDetailsPager
import com.exploramus.shared.viewmodel.screens.details.detailpager.initFavoritesDetailsPager
import com.exploramus.shared.viewmodel.screens.details.singledetail.initCountryDetail
import com.exploramus.shared.viewmodel.screens.favorites.initFavoritesScreen
import com.exploramus.shared.viewmodel.screens.home.initHomeScreen
import com.exploramus.shared.viewmodel.screens.quizzes.choicequiz.initChoiceQuizScreen
import com.exploramus.shared.viewmodel.screens.quizzes.flashcards.initFlashcardScreen
import com.exploramus.shared.viewmodel.screens.quizzes.quizsections.initQuizSectionsScreen
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.initQuizzesListScreen
import com.exploramus.shared.viewmodel.screens.search.initSearchScreen
import com.exploramus.shared.viewmodel.screens.section.initSectionScreen
import com.exploramus.shared.viewmodel.screens.settings.initSettingsScreen

enum class Screen(
    val asString: String,
    val navigationLevel: Int = 1,
    val initSettings: StateManager.(ScreenIdentifier) -> ScreenInitSettings,
) {
    HomeScreen("home", 1, {
        initHomeScreen()
    }),

    FavoritesScreen("favorites", 1, {
        initFavoritesScreen()
    }),

    QuizzesSectionsScreen("quizzessections", 1, {
        initQuizSectionsScreen()
    }),

    QuizzesListScreen("quizzeslist", 2, {
        initQuizzesListScreen(it.screenParams())
    }),

    FlashcardsScreen("flashcards", 3, {
        initFlashcardScreen(it.screenParams())
    }),

    ChoiceQuizScreen("choicequiz", 3, {
        initChoiceQuizScreen(it.screenParams())
    }),

    CountryDetail("detail", 3, {
        initCountryDetail(it.screenParams())
    }),

    DetailsPagerScreen("detailspager", 3, {
        initDetailsPager(it.screenParams())
    }),

    FavoritesPagerScreen("favoritesspager", 3, {
        initFavoritesDetailsPager(it.screenParams())
    }),

    SectionScreen("section", 2, {
        initSectionScreen(params = it.screenParams())
    }),

    SearchScreen("search", 2, {
        initSearchScreen()
    }),

    SettingsScreen("settings", 2, {
        initSettingsScreen()
    }),

    Lv1SettingsScreen("lv1settings", 1, {
        initSettingsScreen()
    }),

    Lv1SearchScreen("lv1search", 1, {
        initSearchScreen()
    }),


}