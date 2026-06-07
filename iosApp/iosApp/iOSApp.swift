import SwiftUI
//import Shared

@main
struct iOSApp: App {
    init() {
       // KoinInitKt.doInitKoin()  // ← start Koin first
      //  KoinInitKt.doInitKoin()
     
        
    }
    
 //   @StateObject var appObj = AppObservableObject()
//    @Environment(\.scenePhase) var scenePhase
    
    var body: some Scene {
        WindowGroup {
          Text("hello")
//            Router()
//                .environmentObject(appObj)
//                .onChange(of: scenePhase) { _, newPhase in
//                    if newPhase == .active {
//                        appObj.dkmpNav.onEnterForeground()
//                    }
//                    else if newPhase == .background {
//                        appObj.dkmpNav.onEnterBackground()
//                    }
//                }
        }
    }
}
