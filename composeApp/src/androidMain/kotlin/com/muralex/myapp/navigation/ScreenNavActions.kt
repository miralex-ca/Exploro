package com.muralex.myapp.navigation

import com.muralex.models.CountryListItem
import com.muralex.myapp.viewmodel.screens.Level1Navigation
import com.muralex.myapp.viewmodel.screens.Screen
import com.muralex.myapp.viewmodel.screens.countrydetail.CountryDetailParams
import com.muralex.myapp.viewmodel.screens.section.SectionParams

interface ScreenNavActions {
    val appNavController: AppNavigationController

    fun navigateBack()

    fun toSearch()

    fun toSettings()

    fun toDetailFromList(item: CountryListItem)

    fun toSection(section: String)

    fun toLevel1Screen(level1Navigation: Level1Navigation)

    class Default(
        override val appNavController: AppNavigationController
    ) : ScreenNavActions {

        override fun navigateBack() {
            appNavController.navigateBack()
        }

        override fun toSearch() {
            appNavController.navigate(Screen.SearchScreen)
        }

        override fun toSettings() {
            appNavController.navigate(Screen.SettingsScreen)
        }

        override fun toDetailFromList(item: CountryListItem) {
            appNavController.navigate(
                Screen.CountryDetail,
                CountryDetailParams(
                    countryCode = item.id,
                    screenTitle = item.name
                )
            )
        }

        override fun toSection(section: String) {
            appNavController.navigate(
                Screen.SectionScreen,
                SectionParams(section, screenTitle = section)
            )
        }

        override fun toLevel1Screen(level1Navigation: Level1Navigation) {
            appNavController.navigateByLevel1(level1Navigation)
        }
    }

}