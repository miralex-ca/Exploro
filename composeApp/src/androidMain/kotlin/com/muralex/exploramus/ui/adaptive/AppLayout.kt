package com.muralex.exploramus.ui.adaptive

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalAppLayout = compositionLocalOf { AppLayoutDefaults.Phone }

val MaterialTheme.layout: AppLayout
    @Composable get() = LocalAppLayout.current

data class AppLayout(
    val screenHorizontalPadding: Dp,
    val sectionHorizontalPadding: Dp,
    val cardSpacing: Dp,
    val sectionArrowSize: Dp,
    val sectionArrowIconSize: Dp,
    var showSearchFab: Boolean,
    val homeCard: HomeCard,
) {
    data class HomeCard(
        val width: Dp,
        val imageHeight: Dp,
    )
}

object AppLayoutDefaults {
    val Phone = AppLayout(
        screenHorizontalPadding = 16.dp,
        sectionHorizontalPadding = 16.dp,
        cardSpacing = 8.dp,
        sectionArrowSize = 32.dp,
        sectionArrowIconSize = 20.dp,
        showSearchFab = true,
        homeCard = AppLayout.HomeCard(
            width = 140.dp,
            imageHeight = 70.dp,
        ),
    )

    val Tablet = AppLayout(
        screenHorizontalPadding = 24.dp,
        sectionHorizontalPadding = 24.dp,
        cardSpacing = 12.dp,
        sectionArrowSize = 36.dp,
        sectionArrowIconSize = 22.dp,
        showSearchFab = false,
        homeCard = AppLayout.HomeCard(
            width = 180.dp,
            imageHeight = 100.dp,
        ),
    )
}