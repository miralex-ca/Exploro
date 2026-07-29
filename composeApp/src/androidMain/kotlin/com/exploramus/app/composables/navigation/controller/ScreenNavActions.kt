package com.exploramus.app.composables.navigation.controller

import com.exploramus.shared.viewmodel.screens.Level1Navigation
import com.exploramus.shared.viewmodel.screens.Screen
import com.exploramus.shared.viewmodel.screens.details.detailpager.DetailsPagerScreenParams
import com.exploramus.shared.viewmodel.screens.details.detailpager.FavoritesPagerScreenParams
import com.exploramus.shared.viewmodel.screens.details.singledetail.DetailsScreenParams
import com.exploramus.shared.viewmodel.screens.quizzes.groupeditems.GroupedItemsScreenParams
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzesListScreenParams
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzesSectionType
import com.exploramus.shared.viewmodel.screens.section.SectionParams

interface ScreenNavActions {
    val appNavController: AppNavController

    fun navigateBack()

    fun toSearch()

    fun toSettings()

    fun toDetailFromList(item: DetailsNavParams)

    fun toDetailsPagerFromList(item: DetailsPagerNavParams)

    fun toFavoritesPagerFromList(item: FavoritesPagerNavParams)

    fun toSection(section: SectionNavParams)

    fun toQuizList(item: QuizListNavParams)

    fun toGroupedItems(params: GroupedItemsScreenParams)

    fun toLevel1Screen(level1Navigation: Level1Navigation)

    class Default(
        override val appNavController: AppNavController
    ) : ScreenNavActions {

        override fun navigateBack() {
            appNavController.navigateBack()
        }

        override fun toSearch() {
            appNavController.navigate(Screen.SearchScreen)
        }

        override fun toSettings() {
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

        override fun toDetailsPagerFromList(item: DetailsPagerNavParams) {
            appNavController.navigate(
                Screen.DetailsPagerScreen,
                DetailsPagerScreenParams(
                    countryCode = item.id,
                    sectionId = item.section,
                    screenTitle = item.name
                )
            )
        }

        override fun toFavoritesPagerFromList(item: FavoritesPagerNavParams) {
            appNavController.navigate(
                Screen.FavoritesPagerScreen,
                FavoritesPagerScreenParams(
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

        override fun toQuizList(item: QuizListNavParams) {
            appNavController.navigate(
                Screen.QuizzesListScreen,
                QuizzesListScreenParams(
                    sectionId = item.sectionId,
                    sectionType = item.quizType,
                    screenTitle = item.name
                )
            )
        }

        override fun toGroupedItems(params: GroupedItemsScreenParams) {
            appNavController.navigate(Screen.GroupedItemsScreen, params)
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

data class DetailsPagerNavParams(
    val id: String,
    val section: String,
    val name: String,
)

data class FavoritesPagerNavParams(
    val id: String,
    val name: String,
)

data class SectionNavParams(
    val id: String,
    val name: String,
)

data class QuizListNavParams(
    val sectionId: String,
    val quizType: QuizzesSectionType,
    val name: String,
)