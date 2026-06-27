package com.exploramus.app

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.Espresso
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class AppNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun goHome() {
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule
                .onAllNodesWithText("Browse")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule
            .onNodeWithText("Browse")
            .performClick()
    }

    private fun waitForAppReady() {
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule
                .onAllNodesWithText("Browse")
                .fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun test1_appLaunchesAndShowsHomeScreen() {
        waitForAppReady()
        composeTestRule
            .onNodeWithText("Browse")
            .assertIsSelected()
    }

    @Test
    fun test2_navigateToFavourites() {
        waitForAppReady()
        composeTestRule
            .onNodeWithText("Favourites")
            .performClick()
        composeTestRule
            .onNodeWithText("Favourites")
            .assertIsSelected()
    }

    @Test
    fun test3_navigateBackToHomeFromFavourites() {
        waitForAppReady()
        composeTestRule
            .onNodeWithText("Favourites")
            .performClick()
        composeTestRule
            .onNodeWithText("Browse")
            .performClick()
        composeTestRule
            .onNodeWithText("Browse")
            .assertIsSelected()
    }

    @Test
    fun test4_openSearchFromTopBar() {
        waitForAppReady()
        composeTestRule
            .onNodeWithContentDescription("Search")
            .performClick()
        composeTestRule.waitUntil(timeoutMillis = 3000) {
            composeTestRule
                .onAllNodesWithText("Search…")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule
            .onNodeWithText("Search…")
            .assertIsDisplayed()

        Espresso.pressBack()
    }

    @Test
    fun test6_openSettingsFromTopBar() {
        waitForAppReady()
        composeTestRule
            .onNodeWithContentDescription("Settings")
            .performClick()
        composeTestRule.waitUntil(timeoutMillis = 3000) {
            composeTestRule
                .onAllNodesWithText("Settings")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule
            .onNodeWithText("Settings")
            .assertIsDisplayed()
    }

    @Test
    fun test7_navigateHomeToSectionToDetails() {
        waitForAppReady()
        composeTestRule.waitUntil(timeoutMillis = 3000) {
            composeTestRule
                .onAllNodesWithContentDescription("See more")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule
            .onAllNodesWithContentDescription("See more")
            .onFirst()
            .performClick()

        composeTestRule.waitUntil(timeoutMillis = 3000) {
            composeTestRule
                .onAllNodesWithText("Europe")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule
            .onNodeWithText("Europe")
            .assertIsDisplayed()

        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule
                .onAllNodesWithText("Albania")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule
            .onNodeWithText("Albania")
            .performClick()

        composeTestRule.waitUntil(timeoutMillis = 3000) {
            composeTestRule
                .onAllNodesWithTag("details_title")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule
            .onNodeWithTag("details_title")
            .assertIsDisplayed()
    }
}