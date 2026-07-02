package com.exploramus.app.composables.navigation.handlers

import com.exploramus.app.composables.navigation.controller.ScreenNavActions
import com.exploramus.app.composables.screens.details.DetailsEventHandler
import com.exploramus.app.composables.screens.details.DetailsPagerEventHandler
import com.exploramus.app.composables.screens.favorites.FavoritesEventHandler
import com.exploramus.app.composables.screens.home.HomeEventHandler
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

    val section by lazy {
        SectionEventHandler(navActions)
    }

    val details by lazy {
        DetailsEventHandler(events, navActions)
    }

    val detailsPager by lazy {
        DetailsPagerEventHandler(events, navActions)
    }

    val favorites by lazy {
        FavoritesEventHandler(navActions, events)
    }

    val search by lazy {
        SearchEventHandler(navActions, events)
    }

    val settings by lazy {
        SettingsEventHandler(events)
    }
}