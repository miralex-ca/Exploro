
import SwiftUI


struct AppTheme {
    let background: Color
    let onBackground: Color

    let primary: Color
    let onPrimary: Color

    let surface: Color
    let onSurface: Color
    let surfaceTint: Color

    let surfaceContainer: Color
    let surfaceContainerHigh: Color
    let surfaceContainerHighest: Color

    let surfaceVariant: Color
    let onSurfaceVariant: Color

    let primaryContainer: Color
    let onPrimaryContainer: Color

    let secondaryContainer: Color
    let onSecondaryContainer: Color?

    let topBar: Color
    let onTopBar: Color

    let cardBorder: Color
    let favorite: Color
    let destructive: Color
    let caret: Color
    
    let navBackground: Color
}

 

extension AppTheme {
    static let light = AppTheme(
        background: Color(hex: "#D8DFEC"),
        onBackground: Color(hex: "#001B3E"),

        primary: Color(hex: "#405FB0"),
        onPrimary: .white,

        surface: Color(hex: "#ECF3FA"),
        onSurface: Color(hex: "#001B3E"),
        surfaceTint: Color(hex: "#385BA9"),

        surfaceContainer: Color(hex: "#ECF3FA"),
        surfaceContainerHigh: Color(hex: "#ECF1F6"),
        surfaceContainerHighest: Color(hex: "#F3F5FC"),

        surfaceVariant: Color(hex: "#FFFFFF"),
        onSurfaceVariant: Color(hex: "#44464F"),

        primaryContainer: Color(hex: "#BBC6EF"),
        onPrimaryContainer: Color(hex: "#001946"),

        secondaryContainer: Color(hex: "#D3E5F8"),
        onSecondaryContainer: nil,

        topBar: Color(hex: "#EAF2FA"),
        onTopBar: Color(hex: "#001946"),

        cardBorder: Color(hex: "#CED6E7"),
        favorite: Color(hex: "#FFEB3B"),
        destructive: Color(hex: "#F85146"),
        caret: Color(hex: "#3C5BA9"),
        navBackground: Color(hex: "#E6EAF2")
    )

    static let dark = AppTheme(
        background: Color(hex: "#202934"),
        onBackground: Color(hex: "#E6EAF2"),

        primary: Color(hex: "#64A6EF"),
        onPrimary: .white,

        surface: Color(hex: "#2F3C52"),
        onSurface: Color(hex: "#E6EAF2"),
        surfaceTint: Color(hex: "#90AEEE"),

        surfaceContainer: Color(hex: "#323C4D"),
        surfaceContainerHigh: Color(hex: "#39465B"),
        surfaceContainerHighest: Color(hex: "#364457"),

        surfaceVariant: Color(hex: "#252F3B"),
        onSurfaceVariant: Color(hex: "#BECCE0"),

        primaryContainer: Color(hex: "#2C5296"),
        onPrimaryContainer: Color(hex: "#EAF0FF"),

        secondaryContainer: Color(hex: "#32455C"),
        onSecondaryContainer: Color(hex: "#C2DDFC"),

        topBar: Color(hex: "#2A3548"),
        onTopBar: Color(hex: "#EAF0FF"),

        cardBorder: Color(hex: "#424E67"),
        favorite: Color(hex: "#FFEB3B"),
        destructive: Color(hex: "#F85146"),
        caret: Color(hex: "#7FA7FF"),
        navBackground: Color(hex: "#060E18")
    )
}

extension AppTheme {
    var cardSurface: Color { surfaceContainerHighest }
    var navSelected: Color { surfaceTint }
    var navText: Color { onSurfaceVariant }
    var navSelectedContainer: Color { primaryContainer }
}

extension AppTheme {
    static func from(_ colorScheme: ColorScheme) -> AppTheme {
        switch colorScheme {
        case .dark:
            return .dark
        default:
            return .light
        }
    }
}


struct AppThemeKey: EnvironmentKey {
    static let defaultValue = AppTheme.from(.light)
}

extension EnvironmentValues {
    var appTheme: AppTheme {
        get { self[AppThemeKey.self] }
        set { self[AppThemeKey.self] = newValue }
    }
}


extension SwiftUI.Color {
    init(hex: String) {
        let hex = hex.trimmingCharacters(in: CharacterSet.alphanumerics.inverted)
        var int: UInt64 = 0
        Scanner(string: hex).scanHexInt64(&int)
        let a, r, g, b: UInt64
        switch hex.count {
        case 3: // RGB (12-bit)
            (a, r, g, b) = (255, (int >> 8) * 17, (int >> 4 & 0xF) * 17, (int & 0xF) * 17)
        case 6: // RGB (24-bit)
            (a, r, g, b) = (255, int >> 16, int >> 8 & 0xFF, int & 0xFF)
        case 8: // ARGB (32-bit)
            (a, r, g, b) = (int >> 24, int >> 16 & 0xFF, int >> 8 & 0xFF, int & 0xFF)
        default:
            (a, r, g, b) = (1, 1, 1, 0)
        }
        
        self.init(
            .sRGB,
            red: Double(r) / 255,
            green: Double(g) / 255,
            blue:  Double(b) / 255,
            opacity: Double(a) / 255
        )
    }
}
