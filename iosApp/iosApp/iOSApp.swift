import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        KoinInitKt.doInitKoin()
    }
    
    @StateObject var appObj = AppObservableObject()
    @State var appLayout = AppLayout()
    @Environment(\.scenePhase) var scenePhase
    
    var body: some Scene {
        WindowGroup {
            MainView()
                .environmentObject(appObj)
                .environment(\.appLayout, appLayout)
                .background(Color("AppBackground").ignoresSafeArea())
                .onChange(of: scenePhase) { _, newPhase in
                    if newPhase == .active {
                        appObj.dkmpNav.onEnterForeground()
                    }
                    else if newPhase == .background {
                        appObj.dkmpNav.onEnterBackground()
                    }
                }
        }
    }
}


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
    
    @ViewBuilder
    private var startupContent: some View {
        switch appObj.startupState {
        case is AppStartupStateLoading:
            AppLoadingScreen()
            
        case let failure as AppStartupStateFailure:
            AppErrorScreen(
                failedAfterSync: failure is AppStartupStateFailureAfterSync,
                onRetry: { appObj.dkmpNav.events.retryBootstrapApp() }
            )
            
        case is AppStartupStateReady:
            Router()
            
        default:
            AppLoadingScreen()
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






