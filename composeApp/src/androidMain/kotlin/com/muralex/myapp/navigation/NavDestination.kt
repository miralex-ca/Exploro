package com.muralex.myapp.navigation

import com.muralex.models.CountryListItem
import com.muralex.myapp.viewmodel.screens.Screen
import com.muralex.myapp.viewmodel.screens.countrydetail.CountryDetailParams
import com.muralex.myapp.viewmodel.screens.section.SectionParams

sealed class NavDestination {
    data object Back : NavDestination()
    data class ToDetailFromList(val item: CountryListItem) : NavDestination()
    data class ToSection(val section: String) : NavDestination()
    data object ToSearch : NavDestination()
}

interface ScreenNavigator {
    val appNavigator: AppNavigator

    fun handle(destination: NavDestination) {
        when (destination) {
            is NavDestination.Back -> appNavigator.navigateBack()
            is NavDestination.ToSearch -> appNavigator.navigate(Screen.SearchScreen)
            is NavDestination.ToDetailFromList -> appNavigator.navigate(
                Screen.CountryDetail,
                CountryDetailParams(
                    countryCode = destination.item.id,
                    screenTitle = destination.item.name,
                )
            )

            is NavDestination.ToSection -> {
                appNavigator.navigate(
                    Screen.SectionScreen,
                    SectionParams(destination.section, screenTitle = destination.section)
                )
            }
        }
    }

    class Default(override val appNavigator: AppNavigator) : ScreenNavigator
}

fun ScreenNavigator.toDetailFromList(item: CountryListItem) {
    handle(NavDestination.ToDetailFromList(item))
}

fun ScreenNavigator.toSection(section: String) {
    handle(NavDestination.ToSection(section))
}
