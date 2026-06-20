import SwiftUI
import Shared

struct MainView: View {
    @EnvironmentObject var appObj: AppObservableObject
    @Environment(\.appLayout) var appLayout
    @Environment(\.colorScheme) var colorScheme
    @Environment(\.horizontalSizeClass) var horizontal
    @Environment(\.verticalSizeClass) var vertical
    
    var body: some View {
        GeometryReader { geo in
            Router()
            .environment(\.appTheme, AppTheme.from(colorScheme))
            .preferredColorScheme(resolveColorScheme(appObj.appEnvironment.themeMode))
            .onChange(of: geo.size, initial: true) { _, size in
                appLayout.update(horizontal: horizontal, vertical: vertical, size: size)
            }
            .task {
                await appObj.collectAppEnvironment()
            }
            .task {
                await appObj.collectAppstartupState()
            }
        }
    }
}

func resolveColorScheme(_ mode: ThemeMode, system: ColorScheme? = nil) -> ColorScheme? {
    switch mode {
    case .dark: return .dark
    case .light: return .light
    case .system: return system
    }
}
