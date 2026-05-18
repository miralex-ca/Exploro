package com.muralex.myapp.navigation

import com.muralex.models.CountryListItem
import com.muralex.myapp.viewmodel.screens.Screen
import com.muralex.myapp.viewmodel.screens.countrydetail.CountryDetailParams
import com.muralex.myapp.viewmodel.screens.section.SectionParams

interface ScreenNavigator {

    val appNavigator: AppNavigator

    fun navigateBack()

    fun toSearch()

    fun toSettings()

    fun toDetailFromList(item: CountryListItem)

    fun toSection(section: String)

    class Default(
        override val appNavigator: AppNavigator
    ) : ScreenNavigator {

        override fun navigateBack() {
            appNavigator.navigateBack()
        }

        override fun toSearch() {
            appNavigator.navigate(Screen.SearchScreen)
        }

        override fun toSettings() {
            appNavigator.navigate(Screen.SettingsScreen)
        }

        override fun toDetailFromList(item: CountryListItem) {
            appNavigator.navigate(
                Screen.CountryDetail,
                CountryDetailParams(
                    countryCode = item.id,
                    screenTitle = item.name
                )
            )
        }

        override fun toSection(section: String) {
            appNavigator.navigate(
                Screen.SectionScreen,
                SectionParams(section, screenTitle = section)
            )
        }
    }

}