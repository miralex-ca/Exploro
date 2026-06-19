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
            AppStartupContent(
                state: appObj.startupState,
                onRetry: { appObj.dkmpNav.events.retryBootstrapApp() }
            ) {
                Router()
            }
            .environment(\.appTheme, AppTheme.from(colorScheme))
            .preferredColorScheme(colorScheme(from: appObj.appEnvironment.themeMode))
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
    
 
    func colorScheme(from themeMode: ModelsThemeMode) -> ColorScheme? {
        switch themeMode {
        case .dark: return .dark
        case .light: return .light
        default: return nil
        }
    }
}
