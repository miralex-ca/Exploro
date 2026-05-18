package com.muralex.myapp.navigation

import com.muralex.myapp.screens.details.DetailsEventHandler
import com.muralex.myapp.screens.favorites.FavoritesEventHandler
import com.muralex.myapp.screens.home.HomeEventHandler
import com.muralex.myapp.screens.search.SearchEventHandler
import com.muralex.myapp.screens.section.SectionEventHandler
import com.muralex.myapp.screens.settings.SettingsEventHandler
import com.muralex.myapp.viewmodel.Events

class EventHandlers(
    private val events: Events,
    private val navigator: ScreenNavigator,
) {
    val home by lazy {
        HomeEventHandler(navigator)
    }

    val section by lazy {
        SectionEventHandler(navigator)
    }

    val details by lazy {
        DetailsEventHandler(events)
    }

    val favorites by lazy {
        FavoritesEventHandler(navigator, events)
    }

    val search by lazy {
        SearchEventHandler(navigator, events)
    }

    val settings by lazy {
        SettingsEventHandler(events)
    }
}