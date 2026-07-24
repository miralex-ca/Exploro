package com.exploramus.app.design.adaptive

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val LocalAppLayout = staticCompositionLocalOf<AppLayout> {
    error("LocalAppLayout not provided — wrap with CompositionLocalProvider at root")
}

val MaterialTheme.layout: AppLayout
    @Composable get() = LocalAppLayout.current

data class AppLayout(
    val cardSpacing: Adp,
    val home: Home,
    val favorites: Favorites,
    val section: Section,
    val details: Details,
    val search: Search,
    val settings: Settings,
    val quizzesSection: QuizzesSection,
    var showSearchFab: AdBool,
    val homeCard: HomeCard,
    val sectionCard: SectionCard,
    val flashcard: Flashcard,
    val quiz: Quiz,
) {


    data class Home(
        val horizontalPadding: Adp,
        val topPadding: Adp,
        val bottomPadding: Adp,
    )

    data class Section(
        val horizontalPadding: Adp,
        val topPadding: AdpH,
        val bottomPadding: Adp,
        val cardSpacing: Adp
    )

    data class Details(
        val maxWidth: Adp,
        val horizontalPadding:Adp,
        val topPadding: Adp,
        val bottomPadding: Adp,
        val cardCorner: Adp,
        val imageHeight: Adp,
        val imageCorner: Adp,
        val titleFontSize: AdSp,
        val coatsOfArmsSize: Adp,
        val coatsTextSpace: Adp,
        val infoCardHorizontalPadding: Adp,
        val infoCardVerticalPadding: Adp,
        val infoCardIconEndSpace: Adp,
        val infoRowVerticalPadding: Adp,
    )

    data class Flashcard(
        val horizontalPadding: Adp,
        val topPadding: Adp,
        val cardMaxWidth: AdaptiveSizeValue<Dp>,
        val landScapeCardMaxWidth: AdaptiveSizeValue<Dp>,
        val maxHeight: AdpH,
        val cardBottomPadding: AdpH,
        val bottomBarPadding: AdpH,
        val cardHorizontalPadding: Adp,
    )

    data class Quiz(
        val topPadding: AdaptiveSizeValue<Dp>,
        val cardHorizontalPadding: Adp,
        val cardMaxWidth: AdaptiveSizeValue<Dp>,
        val maxHeight: AdaptiveSizeValue<Dp>,
        val landScapeCardMaxWidth: AdaptiveSizeValue<Dp>,
        val qustionTextVerticalAlign: Float,
        val optionsHorizontalPadding: AdaptiveSizeValue<Dp>,
        val gridHorizontalPadding: AdaptiveSizeValue<Dp>,
        val optionsTopPadding: AdaptiveSizeValue<Dp>,
        val optionsBottomPadding: AdaptiveSizeValue<Dp>,
        val cardBottomPadding: AdpH,
        val bottomBarPadding: AdpH,
        val resultMaxHeight: AdaptiveSizeValue<Dp>,
        val resultMetricPadding: AdaptiveSizeValue<Dp>,
    )

    data class QuizzesSection(
        val itemMaxWidth: Adp,
        val horizontalPadding: Adp,
        val topPadding: Adp,
        val bottomPadding: Adp,
    )



    data class HomeCard(
        val width: Adp,
        val imageHeight: Adp,
    )

    data class Favorites(
        val listItemMaxWidth: Adp,
        val gridItemMaxWidth: Adp,
        val itemImageHeight: Adp,
        val itemGridImageHeight: Adp,
        val bottomPadding: Adp,
        val imageTextSpace: Adp,
    )

    data class Search(
        val listItemMaxWidth: Adp,
        val itemImageWidth: Adp,
        val imageTextSpace: Adp,
    )

    data class Settings(
        val listItemMaxWidth: Adp,
    )

    data class SectionCard(
        val width: Adp,
        val imageHeight: Adp,
    )

    companion object {
        fun build(formFactor: FormFactor) : AppLayout {
            return when {
                formFactor.isCompactHeight && formFactor.isLandscape -> {
                    AppLayouts.compactLandscape()
                }
                else -> AppLayouts.default()
            }
        }
    }
}


object AppLayouts {
    fun default() = AppLayout(
        cardSpacing = adp(2.dp),
        home = AppLayout.Home(
            horizontalPadding = adp(16.dp, 24.dp),
            topPadding = adp(12.dp, 16.dp),
            bottomPadding = adp(36.dp, 46.dp),
        ),

        favorites =  AppLayout.Favorites(
            listItemMaxWidth = adp(420.dp, 500.dp),
            gridItemMaxWidth = adp(200.dp, 100.dp),
            itemImageHeight = adp(52.dp, 60.dp,70.dp),
            itemGridImageHeight = adp(60.dp, 70.dp,80.dp),
            bottomPadding = adp(60.dp),
            imageTextSpace = adp(20.dp, 26.dp),
        ),

        section = AppLayout.Section(
            horizontalPadding = adp(16.dp, 28.dp, 50.dp),
            topPadding = adph(16.dp, 20.dp, 28.dp),
            bottomPadding = adp(36.dp, 46.dp),
            cardSpacing = adp(10.dp, 14.dp, 16.dp)
        ),
        sectionCard = AppLayout.SectionCard(
            width = adp(160.dp, 180.dp, 200.dp),
            imageHeight = adp(100.dp, 110.dp, 115.dp),
        ),

        details = AppLayout.Details(
            maxWidth = adp(420.dp, 580.dp, 750.dp),
            horizontalPadding = adp(16.dp),
            topPadding = adp(26.dp, 60.dp, 50.dp),
            bottomPadding = adp(36.dp),
            cardCorner = adp(16.dp, 20.dp),
            imageHeight = adp(140.dp),
            imageCorner = adp(12.dp, 16.dp),
            titleFontSize = AdSp(28.sp, 28.sp, 26.sp),
            coatsOfArmsSize = adp(58.dp, 64.dp),
            coatsTextSpace = adp(20.dp, 26.dp),
            infoCardHorizontalPadding = adp(24.dp, 36.dp, 40.dp),
            infoCardVerticalPadding = adp(20.dp),
            infoCardIconEndSpace = adp(20.dp, 28.dp, 20.dp),
            infoRowVerticalPadding = adp(10.dp, 14.dp, 18.dp),
        ),
        search = AppLayout.Search(
            listItemMaxWidth = adp(420.dp, 500.dp, 540.dp),
            itemImageWidth = adp(75.dp, 85.dp,100.dp),
            imageTextSpace = adp(16.dp),
        ),
        settings = AppLayout.Settings(
            listItemMaxWidth = adp(420.dp, 560.dp, 620.dp),
        ),
        showSearchFab = AdBool(true, false, false),
        homeCard = AppLayout.HomeCard(
            width = adp(140.dp, 180.dp),
            imageHeight = adp(70.dp, 100.dp),
        ),
        quizzesSection = AppLayout.QuizzesSection(
            itemMaxWidth = adp(420.dp, 580.dp),
            horizontalPadding = adp(16.dp, 28.dp, 50.dp),
            topPadding = adp(12.dp, 16.dp),
            bottomPadding = adp(60.dp),
        ),
        flashcard = AppLayout.Flashcard(
            horizontalPadding = adp(16.dp, 28.dp, 50.dp),
            topPadding = adp(26.dp, 50.dp, 60.dp),
            cardMaxWidth = adpSize(440.dp, 460.dp, 520.dp),
            landScapeCardMaxWidth = adpSize(450.dp, 700.dp, 860.dp),
            maxHeight = adph(800.dp, 850.dp, 950.dp),
            cardBottomPadding = adph(4.dp, 4.dp, 8.dp, 30.dp),
            bottomBarPadding = adph(16.dp, expandedInCompact = 20.dp),
            cardHorizontalPadding = adp(16.dp, 24.dp, 48.dp),
        ),
        quiz = AppLayout.Quiz(
            topPadding = adpSizeForFormat(phone = 26.dp, phoneLandscape = 12.dp, tablet = 60.dp),
            cardHorizontalPadding = adp(16.dp, 24.dp, 48.dp),
            cardMaxWidth = adpSizeForFormat(
                phone = 440.dp,
                tablet = 520.dp,
                largeTablet = 580.dp,
            ),
            maxHeight = adpSizeForFormat(
                phone = 800.dp,
                tablet = 950.dp,
                tabletLandscape = 580.dp,
                largeTablet = 1050.dp,
                largeTabletLandscape = 650.dp,
            ),
            landScapeCardMaxWidth = adpSizeForFormat(phone = 620.dp, phoneLandscape = 700.dp, tablet = 840.dp, largeTabletLandscape = 900.dp),
            qustionTextVerticalAlign = -0.36F,
            optionsHorizontalPadding = adpSizeForFormat( phone = 20.dp, tablet = 40.dp, largeTablet = 50.dp, largeTabletLandscape = 60.dp),
            gridHorizontalPadding = adpSizeForFormat( phone = 4.dp, tablet = 20.dp, largeTablet = 50.dp, largeTabletLandscape = 60.dp),
            optionsTopPadding = adpSizeForFormat( phone = 10.dp, tablet = 10.dp, tabletLandscape = 20.dp),
            optionsBottomPadding = adpSizeForFormat( phone = 10.dp, largePhone = 40.dp, tablet = 90.dp, tabletLandscape = 20.dp),
            cardBottomPadding = adph(4.dp, 4.dp, 8.dp, 30.dp),
            bottomBarPadding = adph(16.dp, expandedInCompact = 20.dp),
            resultMetricPadding = adpSizeForFormat( phone = 16.dp, phoneLandscape = 4.dp),
            resultMaxHeight = adpSizeForFormat(
                phone = 800.dp,
                tablet = 950.dp,
                tabletLandscape = 580.dp,
            ),
        )
    )

    fun compactLandscape(base: AppLayout = default()): AppLayout {
        return base.copy(
            details = base.details.copy(
                topPadding = adp(26.dp),
                maxWidth = adp(700.dp),
                titleFontSize = AdSp( 26.sp),
                infoCardIconEndSpace = adp(20.dp),
            ),
            homeCard = base.homeCard.copy(
                width = adp(140.dp, 140.dp, 160.dp),
                imageHeight = adp(70.dp, 70.dp,80.dp),
            ),
            quizzesSection =  base.quizzesSection.copy(
                itemMaxWidth = adp(420.dp, 500.dp),
            ),
            flashcard = base.flashcard.copy(
                topPadding = adp(26.dp),
            ),
            quiz = base.quiz.copy(qustionTextVerticalAlign = -0.5f)

        )
    }
}


