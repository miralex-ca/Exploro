package com.exploramus.app.composables.navigation.controller

import com.exploramus.shared.viewmodel.screens.Level1Navigation
import com.exploramus.shared.viewmodel.screens.Screen
import com.exploramus.shared.viewmodel.screens.details.singledetail.DetailsScreenParams
import com.exploramus.shared.viewmodel.screens.section.SectionParams

interface ScreenNavActions {
    val appNavController: AppNavController

    fun navigateBack()

    fun toSearch()

    fun toSettings()

    fun toDetailFromList(item: DetailsNavParams)

    fun toSection(section: SectionNavParams)

    fun toLevel1Screen(level1Navigation: Level1Navigation)

    class Default(
        override val appNavController: AppNavController
    ) : ScreenNavActions {

        override fun navigateBack() {
            appNavController.navigateBack()
        }

        override fun toSearch() {
            appNavController.navigate(Screen.SearchScreen)
           // appNavController.navigateByLevel1(Level1Navigation.Lv1Search)
        }

        override fun toSettings() {
           // appNavController.navigate(Screen.SettingsScreen)
           // appNavController.navigate(Screen.Lv1SettingsScreen)
            appNavController.navigateByLevel1(Level1Navigation.Lv1Settings)
        }

        override fun toDetailFromList(item: DetailsNavParams) {
            appNavController.navigate(
                Screen.CountryDetail,
                DetailsScreenParams(
                    countryCode = item.id,
                    screenTitle = item.name
                )
            )
        }

        override fun toSection(section: SectionNavParams) {
            appNavController.navigate(
                Screen.SectionScreen,
                SectionParams(section.id, screenTitle = section.name)
            )
        }

        override fun toLevel1Screen(level1Navigation: Level1Navigation) {
            appNavController.navigateByLevel1(level1Navigation)
        }
    }
}

data class DetailsNavParams(
    val id: String,
    val name: String,
)

data class SectionNavParams(
    val id: String,
    val name: String,
)