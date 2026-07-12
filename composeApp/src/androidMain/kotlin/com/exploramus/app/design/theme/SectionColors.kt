package com.exploramus.app.design.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class SectionColor(
    val iconLight: Color,
    val iconDark: Color,
    val backgroundLight: Color,
    val backgroundDark: Color,
    val textLight: Color,
    val textDark: Color,
    val onIconLight: Color,
    val onIconDark: Color
) {
    @Composable
    fun icon(): Color = if (isDarkTheme()) iconDark else iconLight

    @Composable
    fun background(): Color = if (isDarkTheme()) backgroundDark else backgroundLight

    @Composable
    fun text(): Color = if (isDarkTheme()) textDark else textLight

    @Composable
    fun onIcon(): Color = if (isDarkTheme()) onIconDark else onIconLight

    @Composable
    private fun isDarkTheme(): Boolean = LocalAppColors.current.isDark
}

object SectionColorPalette {
    val Red = SectionColor(
        iconLight = Color(0xFFE53935),
        iconDark = Color(0xFFEF5350),
        backgroundLight = Color(0xFFFFEBEE),
        backgroundDark = Color(0xFFEF5350).copy(alpha = 0.2f),
        textLight = Color(0xFFD32F2F),
        textDark = Color(0xFFFF8A80),
        onIconLight = Color.White,
        onIconDark = Color.White
    )

    val Pink = SectionColor(
        iconLight = Color(0xFFD81B60),
        iconDark = Color(0xFFEC407A),
        backgroundLight = Color(0xFFFCE4EC),
        backgroundDark = Color(0xFFEC407A).copy(alpha = 0.2f),
        textLight = Color(0xFFC2185B),
        textDark = Color(0xFFFF80AB),
        onIconLight = Color.White,
        onIconDark = Color.White
    )

    val Purple = SectionColor(
        iconLight = Color(0xFF8E24AA),
        iconDark = Color(0xFFAB47BC),
        backgroundLight = Color(0xFFF3E5F5),
        backgroundDark = Color(0xFFAB47BC).copy(alpha = 0.2f),
        textLight = Color(0xFF7B1FA2),
        textDark = Color(0xFFEA80FC),
        onIconLight = Color.White,
        onIconDark = Color.White
    )

    val DeepPurple = SectionColor(
        iconLight = Color(0xFF5E35B1),
        iconDark = Color(0xFF7E57C2),
        backgroundLight = Color(0xFFEDE7F6),
        backgroundDark = Color(0xFF7E57C2).copy(alpha = 0.2f),
        textLight = Color(0xFF512DA8),
        textDark = Color(0xFFB388FF),
        onIconLight = Color.White,
        onIconDark = Color.White
    )

    val Indigo = SectionColor(
        iconLight = Color(0xFF3949AB),
        iconDark = Color(0xFF5C6BC0),
        backgroundLight = Color(0xFFE8EAF6),
        backgroundDark = Color(0xFF5C6BC0).copy(alpha = 0.2f),
        textLight = Color(0xFF303F9F),
        textDark = Color(0xFF8C9EFF),
        onIconLight = Color.White,
        onIconDark = Color.White
    )

    val Blue = SectionColor(
        iconLight = Color(0xFF1E88E5),
        iconDark = Color(0xFF42A5F5),
        backgroundLight = Color(0xFFE3F2FD),
        backgroundDark = Color(0xFF42A5F5).copy(alpha = 0.2f),
        textLight = Color(0xFF1976D2),
        textDark = Color(0xFF82B1FF),
        onIconLight = Color.White,
        onIconDark = Color.White
    )

    val LightBlue = SectionColor(
        iconLight = Color(0xFF039BE5),
        iconDark = Color(0xFF29B6F6),
        backgroundLight = Color(0xFFE1F5FE),
        backgroundDark = Color(0xFF29B6F6).copy(alpha = 0.2f),
        textLight = Color(0xFF0288D1),
        textDark = Color(0xFF80D8FF),
        onIconLight = Color.White,
        onIconDark = Color.White
    )

    val Cyan = SectionColor(
        iconLight = Color(0xFF00ACC1),
        iconDark = Color(0xFF26C6DA),
        backgroundLight = Color(0xFFE0F7FA),
        backgroundDark = Color(0xFF26C6DA).copy(alpha = 0.2f),
        textLight = Color(0xFF0097A7),
        textDark = Color(0xFF84FFFF),
        onIconLight = Color.White,
        onIconDark = Color.White
    )

    val Teal = SectionColor(
        iconLight = Color(0xFF00897B),
        iconDark = Color(0xFF26A69A),
        backgroundLight = Color(0xFFE0F2F1),
        backgroundDark = Color(0xFF26A69A).copy(alpha = 0.2f),
        textLight = Color(0xFF00796B),
        textDark = Color(0xFFA7FFEB),
        onIconLight = Color.White,
        onIconDark = Color.White
    )

    val Green = SectionColor(
        iconLight = Color(0xFF43A047),
        iconDark = Color(0xFF66BB6A),
        backgroundLight = Color(0xFFE8F5E9),
        backgroundDark = Color(0xFF66BB6A).copy(alpha = 0.2f),
        textLight = Color(0xFF388E3C),
        textDark = Color(0xFFB9F6CA),
        onIconLight = Color.White,
        onIconDark = Color.White
    )

    val LightGreen = SectionColor(
        iconLight = Color(0xFF7CB342),
        iconDark = Color(0xFF9CCC65),
        backgroundLight = Color(0xFFF1F8E9),
        backgroundDark = Color(0xFF9CCC65).copy(alpha = 0.2f),
        textLight = Color(0xFF689F38),
        textDark = Color(0xFFCCFF90),
        onIconLight = Color.White,
        onIconDark = Color.White
    )

    val Lime = SectionColor(
        iconLight = Color(0xFFC0CA33),
        iconDark = Color(0xFFD4E157),
        backgroundLight = Color(0xFFF9FBE7),
        backgroundDark = Color(0xFFD4E157).copy(alpha = 0.2f),
        textLight = Color(0xFF9E9D24),
        textDark = Color(0xFFF4FF81),
        onIconLight = Color.White,
        onIconDark = Color.White
    )

    val Yellow = SectionColor(
        iconLight = Color(0xFFF9A825),
        iconDark = Color(0xFFFBC02D),
        backgroundLight = Color(0xFFFFFDE7),
        backgroundDark = Color(0xFFFBC02D).copy(alpha = 0.2f),
        textLight = Color(0xFFE65100), // Darkened for better contrast on titles
        textDark = Color(0xFFFFFF8D),
        onIconLight = Color.White,
        onIconDark = Color.White
    )

    val Amber = SectionColor(
        iconLight = Color(0xFFFF8F00),
        iconDark = Color(0xFFFFA000),
        backgroundLight = Color(0xFFFFF8E1),
        backgroundDark = Color(0xFFFFA000).copy(alpha = 0.2f),
        textLight = Color(0xFFBF360C), // Darkened for better contrast on titles
        textDark = Color(0xFFFFE57F),
        onIconLight = Color.White,
        onIconDark = Color.White
    )

    val Orange = SectionColor(
        iconLight = Color(0xFFFB8C00),
        iconDark = Color(0xFFFFA726),
        backgroundLight = Color(0xFFFFF3E0),
        backgroundDark = Color(0xFFFFA726).copy(alpha = 0.2f),
        textLight = Color(0xFFEF6C00),
        textDark = Color(0xFFFFD180),
        onIconLight = Color.White,
        onIconDark = Color.White
    )

    val DeepOrange = SectionColor(
        iconLight = Color(0xFFF4511E),
        iconDark = Color(0xFFFF7043),
        backgroundLight = Color(0xFFFBE9E7),
        backgroundDark = Color(0xFFFF7043).copy(alpha = 0.2f),
        textLight = Color(0xFFE64A19),
        textDark = Color(0xFFFF9E80),
        onIconLight = Color.White,
        onIconDark = Color.White
    )

    val Brown = SectionColor(
        iconLight = Color(0xFF6D4C41),
        iconDark = Color(0xFF8D6E63),
        backgroundLight = Color(0xFFEFEBE9),
        backgroundDark = Color(0xFF8D6E63).copy(alpha = 0.2f),
        textLight = Color(0xFF5D4037),
        textDark = Color(0xFFD7CCC8),
        onIconLight = Color.White,
        onIconDark = Color.White
    )

    val BlueGrey = SectionColor(
        iconLight = Color(0xFF546E7A),
        iconDark = Color(0xFF78909C),
        backgroundLight = Color(0xFFECEFF1),
        backgroundDark = Color(0xFF78909C).copy(alpha = 0.2f),
        textLight = Color(0xFF455A64),
        textDark = Color(0xFFCFD8DC),
        onIconLight = Color.White,
        onIconDark = Color.White
    )

    // Specific Mappings
    val Favorites = Amber
    val AllCountries = Blue

    val all = listOf(
        Red, Pink, Purple, DeepPurple, Indigo, Blue, LightBlue, Cyan, Teal, Green,
        LightGreen, Lime, Yellow, Amber, Orange, DeepOrange, Brown, BlueGrey
    )
}
