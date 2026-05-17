package com.muralex.myapp.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val AccentBlue = Color(0xFF405FB0)
val OnAccentBlue = Color.White

//val BluePrimary = Color(0xFFEAF2FA)
//val OnBluePrimary = Color(0xFF001946)

val BlueBackground = Color(0xFFD8DFEC) // background of screen
val OnBlueBackground = Color(0xFF001B3E)

val BlueSurface = Color(0xFFECF3FA) // bottom bar
val onBlueSurface = Color(0xFF001B3E) //ripple effet on surface,
val BlueSurfaceTint = Color(0xFF385BA9)

val BlueSurfaceContainer = Color(0xFFECF3FA)
val BlueSurfaceContainerHigh = Color(0xFFECF1F6) // dialog

val CardLight = Color(0xFFF3F5FC) //Color.White

val BlueSecondaryContainer = Color(0xFFD3E5F8) // selected item

val BlueSurfaceVariant = Color(0xFFFFFFFF)  // search bar
val onBlueSurfaceVariant = Color(0xFF44464F)

val BluePrimaryContainer = Color(0xFFBBC6EF) // fab button
val onBluePrimaryContainer = Color(0xFF001946)


val TopBarContainer = Color(0xFFEAF2FA) // top bar
val OnTopBarContainer = Color(0xFF001946) // top bar text

val CaretColor = Color(0xFF3C5BA9)
val CardBorderLight = Color(0xFFCED6E7)
val DestructiveColor = Color(0xFFF85146)
val FavoriteColor = Color(0xFFFFEB3B)


val AccentBlueDark = Color(0xFF64A6EF)
val OnAccentBlueDark = Color.White

//val DarkPrimary = Color(0xFF2F3C52)
//val OnDarkPrimary = Color(0xFFEAF0FF)

val TopBarContainerDark = Color(0xFF2F3C52) // top bar
val OnTopBarContainerDark = Color(0xFFEAF0FF) // top bar text

val DarkBackground = Color(0xFF202934)
val OnDarkBackground = Color(0xFFE6EAF2)

val DarkSurface = Color(0xFF28303D)
val OnDarkSurface = Color(0xFFE6EAF2)

val DarkSurfaceTint = Color(0xFF6F8FD6)

val DarkCard = Color(0xFF3E4C62)

val DarkSurfaceContainer = Color(0xFF323C4D)
val DarkSurfaceContainerHigh = Color(0xFF39465B) // dialog

val DarkSecondaryContainer = Color(0xFF32455C)

val DarkSurfaceVariant = Color(0xFF252F3B)
val OnDarkSurfaceVariant = Color(0xFFB8C2D6)

val DarkPrimaryContainer = Color(0xFF2C5296)
val OnDarkPrimaryContainer = Color(0xFFEAF0FF)


val CardBorderDark = Color(0xFF333C49)

val DarkCaretColor = Color(0xFF7FA7FF)



val LightColorScheme =  lightColorScheme(
        primary = AccentBlue,
        onPrimary =  OnAccentBlue,

        background = BlueBackground,
        onBackground = OnBlueBackground,

        surface = BlueSurface,
        onSurface = onBlueSurface,
        surfaceTint = BlueSurfaceTint,
        surfaceContainerHighest = CardLight,

    surfaceContainer  = BlueSurfaceContainer,
    surfaceContainerHigh  = BlueSurfaceContainerHigh,

        secondaryContainer = BlueSecondaryContainer,

        surfaceVariant = BlueSurfaceVariant,
        onSurfaceVariant = onBlueSurfaceVariant,

        primaryContainer = BluePrimaryContainer,
        onPrimaryContainer = onBluePrimaryContainer,

    )

val DarkColorScheme = darkColorScheme(
    primary = AccentBlueDark,
    onPrimary = OnAccentBlueDark,

    background = DarkBackground,
    onBackground = OnDarkBackground,

    surface = DarkSurface,
    onSurface = OnDarkSurface,
    surfaceTint = DarkSurfaceTint,

    surfaceContainerHighest = DarkCard,

    surfaceContainer  = DarkSurfaceContainer,
    surfaceContainerHigh  = DarkSurfaceContainerHigh,

    secondaryContainer = DarkSecondaryContainer,

    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = OnDarkSurfaceVariant,

    primaryContainer = DarkPrimaryContainer,
    onPrimaryContainer = OnDarkPrimaryContainer,
)




