import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        KoinInitKt.doInitKoin()
       // UIView.appearance().backgroundColor = UIColor(named: "AppBackground")
        
  
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
            Router()
                .environment(\.appTheme, AppTheme.from(colorScheme))
                .preferredColorScheme(colorScheme(from: appObj.appEnvironment.themeMode))
                .onChange(of: geo.size, initial: true) { _, size in
                    appLayout.update(horizontal: horizontal, vertical: vertical, size: size)
                }
                .task {
                    await appObj.collectAppEnvironment()
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
