import SwiftUI
import ComposeApp

struct MainView: View {
    @EnvironmentObject var appObj: AppObservableObject
    @Environment(\.appLayout) var appLayout
    @Environment(\.colorScheme) var colorScheme
    @Environment(\.horizontalSizeClass) var horizontal
    @Environment(\.verticalSizeClass) var vertical
    
    var body: some View {
        ComposeView(navigation: appObj.dkmpNav)
            .ignoresSafeArea()
            .preferredColorScheme(resolveColorScheme(appObj.appEnvironment.themeMode))
            .task {
                await appObj.collectAppEnvironment()
            }
            .task {
                await appObj.collectAppstartupState()
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
