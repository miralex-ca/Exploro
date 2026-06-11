import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        KoinInitKt.doInitKoin()
       // UIView.appearance().backgroundColor = UIColor(named: "AppBackground")
    }
    
    @StateObject var appObj = AppObservableObject()
    @Environment(\.scenePhase) var scenePhase
    
    var body: some Scene {
        WindowGroup {
            MainView()
                .environmentObject(appObj)
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
    @Environment(\.colorScheme) var colorScheme
    
    var body: some View {
        
        
        
        Router()
            .environment(\.appTheme, AppTheme.from(colorScheme))
            .preferredColorScheme(colorScheme(from: appObj.appEnvironment.themeMode))
            .task {
                await appObj.collectAppEnvironment()
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
