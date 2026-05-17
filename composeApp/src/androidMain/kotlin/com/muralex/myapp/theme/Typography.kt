package com.muralex.myapp.theme


import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight =  20.sp,
    )
)



object AppTypography {

    val homeSectionTitle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
    )

    val searchText = TextStyle(
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp
    )

    val appBarTitle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold
    )

    val sectionTitle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
    )

    val listItemTitle = TextStyle(
        fontSize = 15.sp,
        fontWeight = FontWeight.Medium
    )

    val listItemSubtitle = TextStyle(
        fontSize = 13.sp,
        fontWeight = FontWeight.Normal
    )
}