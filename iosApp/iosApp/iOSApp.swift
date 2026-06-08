import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        KoinInitKt.doInitKoin()
    }
    
    @StateObject var appObj = AppObservableObject()
    @Environment(\.scenePhase) var scenePhase
    
    var body: some Scene {
        WindowGroup {
            MainView()
                .environmentObject(appObj)
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
    
    var body: some View {
        Router()
            .preferredColorScheme(
                appObj.appEnvironment.themeMode == .dark ? .dark :
                    appObj.appEnvironment.themeMode == .light ? .light : nil
            )
            .task {
                await appObj.collectAppEnvironment()
            }
    }
}
