package com.exploramus.app.composables.navigation.handlers

import com.exploramus.app.composables.navigation.controller.ScreenNavActions
import com.exploramus.app.composables.screens.details.DetailsEventHandler
import com.exploramus.app.composables.screens.details.DetailsPagerEventHandler
import com.exploramus.app.composables.screens.favorites.FavoritesEventHandler
import com.exploramus.app.composables.screens.home.HomeEventHandler
import com.exploramus.app.composables.screens.quizzes.choicequiz.ChoiceQuizEventHandler
import com.exploramus.app.composables.screens.quizzes.flashcards.FlashcardEventHandler
import com.exploramus.app.composables.screens.quizzes.groupeditems.GroupedItemsEventHandler
import com.exploramus.app.composables.screens.quizzes.quizsections.QuizSectionsEventHandler
import com.exploramus.app.composables.screens.quizzes.quizzeslist.QuizzesListEventHandler
import com.exploramus.app.composables.screens.search.SearchEventHandler
import com.exploramus.app.composables.screens.section.SectionEventHandler
import com.exploramus.app.composables.screens.settings.SettingsEventHandler
import com.exploramus.shared.viewmodel.core.Events

class EventHandlers(
    private val events: Events,
    private val navActions: ScreenNavActions,
) {
    val home by lazy {
        HomeEventHandler(navActions)
    }

    val favorites by lazy {
        FavoritesEventHandler(navActions, events)
    }

    val quizzesSections by lazy {
        QuizSectionsEventHandler(navActions)
    }

    val quizzesList by lazy {
        QuizzesListEventHandler(navActions, events)
    }

    val flashcards by lazy {
        FlashcardEventHandler(navActions, events)
    }

    val choiceQuiz by lazy {
        ChoiceQuizEventHandler(navActions, events)
    }

    val section by lazy {
        SectionEventHandler(navActions)
    }

    val groupedItems by lazy {
        GroupedItemsEventHandler(navActions)
    }

    val details by lazy {
        DetailsEventHandler(events, navActions)
    }

    val detailsPager by lazy {
        DetailsPagerEventHandler(events, navActions)
    }

    val search by lazy {
        SearchEventHandler(navActions, events)
    }

    val settings by lazy {
        SettingsEventHandler(events)
    }
}