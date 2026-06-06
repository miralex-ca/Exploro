package com.muralex.exploramus.navigation.handlers

import com.muralex.exploramus.navigation.controller.ScreenNavActions
import com.muralex.exploramus.screens.details.DetailsEventHandler
import com.muralex.exploramus.screens.favorites.FavoritesEventHandler
import com.muralex.exploramus.screens.home.HomeEventHandler
import com.muralex.exploramus.screens.search.SearchEventHandler
import com.muralex.exploramus.screens.section.SectionEventHandler
import com.muralex.exploramus.screens.settings.SettingsEventHandler
import com.muralex.exploramus.viewmodel.core.Events

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
        DetailsEventHandler(events)
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