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









