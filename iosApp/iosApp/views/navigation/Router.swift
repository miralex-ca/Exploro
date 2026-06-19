
import SwiftUI
import Shared


struct Router: View {
    @EnvironmentObject var appObj: AppObservableObject
    @Environment(\.appTheme) var theme
    @Environment(\.colorScheme) var systemScheme
    @Environment(\.appLayout) var layout
    
    var body: some View {
        let level1ScreenIdentifiers = getAllLevel1ScreenIdentifiers()
        let level1ScreenIdentifiersWithState = appObj.dkmpNav.stateManager.verticalNavigationLevels
            .map { ($0.value as! Dictionary<Int, ScreenIdentifier>)[1]! }
        let isLevel1 = appObj.localNavigationState.topScreenIdentifier.screen.navigationLevel == 1
        let showBottomBar = isLevel1 && layout.useBottomBar
        
        let screenNavActions = makeNavActions(navigate, navigateByLevel1)
        
        ZStack {
            Color.clear.ignoresSafeArea()
            ForEach(level1ScreenIdentifiers, id: \.self.URI) { screenIdentifier in
                if level1ScreenIdentifiersWithState.contains(where: { $0.URI == screenIdentifier.URI }) {
                    let isActive = screenIdentifier.URI == appObj.dkmpNav.stateManager.currentLevel1ScreenIdentifier?.URI
                    
                    OnePane(
                        level1ScreenIdentifier: screenIdentifier,
                        screenNavActions: screenNavActions
                    )
                    .opacity(isActive ? 1 : 0) 
                }
            }
            
            NavScaffold(screenNavActions: screenNavActions)
            
            if showBottomBar {
                ZStack {
                    bottomGradient
                }
                .safeAreaInset(edge: .bottom) {
                    FloatingTabBar(onSearch: { screenNavActions.toSearch() })
                        .padding(.bottom, 12)
                }
            }
        }
        .background(theme.background)
        .sheet(isPresented: $appObj.showSettings) {
            SettingsView()
                .preferredColorScheme(
                    resolvedScheme(appObj.appEnvironment.themeMode, system: systemScheme)
                )
        }
        
    }
    
    private var bottomGradient: some View {
        VStack {
            Spacer()
            LinearGradient(
                colors: [.clear, theme.bottomGradient],
                startPoint: .top,
                endPoint: .bottom
            )
            .frame(height: 80 + safeAreaBottom)
        }
        .ignoresSafeArea(edges: .all)
    }
    
    func resolvedScheme(_ mode: ModelsThemeMode, system: ColorScheme) -> ColorScheme {
        switch mode {
        case .dark: return .dark
        case .light: return .light
        case .system: return system
        }
    }
    
    private var safeAreaBottom: CGFloat {
        UIApplication.shared.connectedScenes
            .compactMap { $0 as? UIWindowScene }
            .first?.windows.first?.safeAreaInsets.bottom ?? 0
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
}



func getAllLevel1ScreenIdentifiers() -> [ScreenIdentifier] {
    return Level1Navigation.allCases.map { l1Navigation in
        l1Navigation.screenIdentifier
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









 
