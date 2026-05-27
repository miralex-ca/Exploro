package com.muralex.myapp.navigation

import com.muralex.myapp.viewmodel.screens.Level1Navigation
import com.muralex.myapp.viewmodel.screens.Screen
import com.muralex.myapp.viewmodel.screens.countrydetail.DetailsScreenParams
import com.muralex.myapp.viewmodel.screens.section.SectionParams

interface ScreenNavActions {
    val appNavController: AppNavigationController

    fun navigateBack()

    fun toSearch()

    fun toSettings()

    fun toDetailFromList(item: DetailsNavParams)

    fun toSection(section: SectionNavParams)

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