
import SwiftUI
import ComposeApp


struct Router: View {
    @EnvironmentObject var appObj: AppObservableObject
    @Environment(\.appTheme) var theme
    @Environment(\.colorScheme) var systemScheme
    
    var body: some View {
        let level1ScreenIdentifiers = getAllLevel1ScreenIdentifiers()
        let level1ScreenIdentifiersWithState = appObj.dkmpNav.stateManager.verticalNavigationLevels
            .map { ($0.value as! Dictionary<Int, ScreenIdentifier>)[1]! }
        
        let screenNavActions = makeNavActions(navigate, navigateByLevel1, openSettings)
        
        AppStartupContent(
            onRetry: { appObj.dkmpNav.events.retryBootstrapApp() }
        ) {
            ZStack {
                Color.clear.ignoresSafeArea()
                ForEach(level1ScreenIdentifiers, id: \.self.URI) { screenIdentifier in
                    if level1ScreenIdentifiersWithState.contains(where: { $0.URI == screenIdentifier.URI }) {
                        let isActive = screenIdentifier.URI == appObj.dkmpNav.stateManager.currentLevel1ScreenIdentifier?.URI
                        
                        NavigationStackHost(
                            level1ScreenIdentifier: screenIdentifier,
                            screenNavActions: screenNavActions
                        )
                        .opacity(isActive ? 1 : 0)
                    }
                }
                
                NavigationControls(navigationActions: screenNavActions)
                
            }
            .background(theme.background)
            .sheet(isPresented: $appObj.showSettings) {
                SettingsView()
                    .preferredColorScheme(
                        resolveColorScheme(appObj.appEnvironment.themeMode, system: systemScheme)
                    )
            }
        }
    }
    

    
    func navigate(_ screen: Screen, _ params: ScreenParams?) {
        let sId = appObj.dkmpNav.navigate(screen, params)
        let level1ScreenIdentifier = appObj.dkmpNav.navigationState.currentLevel1ScreenIdentifier
        
        $appObj.localNavigationState.paths
            .getPath(level1URI: level1ScreenIdentifier.URI)
            .wrappedValue.append(sId)
    }
    
    func navigateByLevel1(level1Navigation: Level1Navigation) {
        appObj.dkmpNav.navigateByLevel1Menu(appObj, level1Navigation: level1Navigation)
    }
    
    func openSettings() {
        appObj.showSettings = true
    }
}

func getAllLevel1ScreenIdentifiers() -> [ScreenIdentifier] {
    return Level1Navigation.allCases.map { l1Navigation in
        l1Navigation.screenIdentifier
    }
}

extension Navigation {
    func makeEventHandlers(_ actions: NavigationActions) -> EventHandlers {
        return EventHandlers(events: events, navActions: actions)
    }
}

extension Navigation {
    
    func navigate(_ screen: Screen, _ params: ScreenParams?) -> ScreenIdentifier {
        return ScreenIdentifier.Factory().get(screen: screen, params: params)
    }

    func navigateByLevel1Menu(_ appObj: AppObservableObject, level1Navigation: Level1Navigation) {
        selectLevel1Navigation(level1ScreenIdentifier: level1Navigation.screenIdentifier) // shared navigationState is updated
        appObj.localNavigationState = navigationState // update localNavigationState
    }
    
}

