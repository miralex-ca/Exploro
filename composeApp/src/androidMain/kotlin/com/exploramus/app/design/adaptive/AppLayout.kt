package com.exploramus.app.design.adaptive

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
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

    data class QuizzesSection(
        val itemMaxWidth: Adp,
        val horizontalPadding:Adp,
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

        )
    }
}


