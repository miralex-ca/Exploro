
import SwiftUI
import ComposeApp

struct NavigationStackHost: View {
    var level1ScreenIdentifier : ScreenIdentifier
    let screenNavActions: NavigationActions
    
    @EnvironmentObject var appObj: AppObservableObject
    @Environment(\.appTheme) var theme

    var body: some View {
        let isLevel1 = appObj.localNavigationState.topScreenIdentifier.screen.navigationLevel == 1
        
        NavigationStack(path: $appObj.localNavigationState.paths.getPath(level1URI: level1ScreenIdentifier.URI)) {
            ScreenPicker(
                requestedSId: level1ScreenIdentifier,
                level1ScreenIdentifier: level1ScreenIdentifier,
                eventHandlers: appObj.dkmpNav.makeEventHandlers(screenNavActions)
            )
            .navigationDestination(for: ScreenIdentifier.self) { sId in
                let _ = appObj.dkmpNav.navigateToScreenForIos(screenIdentifier: sId, level1ScreenIdentifier: level1ScreenIdentifier)
                ScreenPicker(
                    requestedSId: sId,
                    level1ScreenIdentifier: level1ScreenIdentifier,
                    eventHandlers: appObj.dkmpNav.makeEventHandlers(screenNavActions)
                )
            }
        }
        .toolbar(isLevel1 ? .visible : .hidden, for: .tabBar)
    }
}


// this is used to bind a path defined in Kotlin's shared code, to the NavigationStack path
// N.B. in our Kotlin code, paths are stored as MutableMap<String,MutableList<ScreenIdentifier>>,
//     where "String" is the Level1ScreenIdentifier and "MutableList<ScreenIdentifier>" is the path
extension Binding where Value == KotlinMutableDictionary<NSString,NSMutableArray> {
    func getPath(level1URI: String) -> Binding<[ScreenIdentifier]> {
        return Binding<[ScreenIdentifier]>(
            get: {
                let dict = self.wrappedValue as! [String:[ScreenIdentifier]]
                return dict[level1URI] ?? []
            },
            set: {
                var writableDict = self.wrappedValue as! [NSString:NSMutableArray]
                writableDict[level1URI as NSString] = NSMutableArray(array: $0)
                self.wrappedValue = KotlinMutableDictionary<NSString, NSMutableArray>.init(dictionary: writableDict)
            }
        )
    }
}
