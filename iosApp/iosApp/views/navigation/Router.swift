
import SwiftUI
import Shared


struct Router: View {
    @EnvironmentObject var appObj: AppObservableObject
    @Environment(\.appTheme) var theme
    
    var body: some View {
        let level1ScreenIdentifiers = getAllLevel1ScreenIdentifiers()
        let level1ScreenIdentifiersWithState = appObj.dkmpNav.stateManager.verticalNavigationLevels
            .map { ($0.value as! Dictionary<Int, ScreenIdentifier>)[1]! }
        let isLevel1 = appObj.localNavigationState.topScreenIdentifier.screen.navigationLevel == 1
        
        let screenNavActions = makeNavActions(navigate, navigateByLevel1)
        
        ZStack {
            Color.clear.ignoresSafeArea()
            ForEach(level1ScreenIdentifiers, id: \.self.URI) { screenIdentifier in
                if level1ScreenIdentifiersWithState.contains(where: { $0.URI == screenIdentifier.URI }) {
                    OnePane(
                        level1ScreenIdentifier: screenIdentifier,
                        screenNavActions: screenNavActions
                    )
                    .opacity(screenIdentifier.URI == appObj.dkmpNav.stateManager.currentLevel1ScreenIdentifier?.URI ? 1 : 0)
                    
                } else {
                    EmptyView()
                }
            }
            
            if isLevel1 {
                VStack {
                    Spacer()
                    FloatingTabBar(
                        onSearch: { navigate(.searchScreen, nil) }
                    )
                }
            }
            
        }
       // .ignoresSafeArea()
        
        .background(theme.background)
        
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


struct FloatingTabBar: View {
    @EnvironmentObject var appObj: AppObservableObject
    var onSearch: () -> Void
    
    @Environment(\.appTheme) var theme

    var body: some View {
        let currentURI = appObj.localNavigationState.currentLevel1ScreenIdentifier.URI

        HStack(spacing: 12) {
            HStack(spacing: 16) {
                FloatingTabButton(
                    label: "Browse",
                    icon: "safari",
                    selectedIcon: "safari.fill",
                    selected: currentURI == Level1Navigation.home.screenIdentifier.URI
                ) {
                    appObj.dkmpNav.navigateByLevel1Menu(appObj, level1Navigation: .home)
                }
                FloatingTabButton(
                    label: "Favorites",
                    icon: "star",
                    selectedIcon: "star.fill",
                    selected: currentURI == Level1Navigation.favorites.screenIdentifier.URI
                ) {
                    appObj.dkmpNav.navigateByLevel1Menu(appObj, level1Navigation: .favorites)
                }
            }
            .padding(.horizontal, 8)
            .padding(.vertical, 6)
            .glassCapsule()
 
            Button(action: onSearch) {
                Image(systemName: "magnifyingglass")
                    .font(.system(size: 28))
                    .foregroundColor(theme.navText)
                    .padding(18)
                    .glassCapsule()
            }
            .buttonStyle(.plain)
        }
        .padding(.bottom, 16)
        .background(Color.black.opacity(0.001))
    }
}


struct FloatingTabButton: View {
    let label: String
    let icon: String
    let selectedIcon: String
    let selected: Bool
    let onClick: () -> Void
    
    @Environment(\.appTheme) var theme

    var body: some View {
        Button(action: onClick) {
            VStack(spacing: 3) {
                Image(systemName: selected ? selectedIcon : icon)
                    .font(.system(size: 20))
                Text(label)
                    .font(.caption2)
            }
            .foregroundStyle(selected ? theme.navSelected: theme.navText)
            .padding(.horizontal, 24)
            .padding(.vertical, 8)
            .background {
                if selected {
                    Capsule()
                        .fill(theme.navSelectedContainer.opacity(0.3))
                }
            }
        }
        .buttonStyle(.plain)
    }
}


struct GlassCapsuleModifier: ViewModifier {
    func body(content: Content) -> some View {
        if #available(iOS 26, *) {
            content
                .glassEffect(.regular, in: Capsule())
        } else {
            content
                .background(
                    .ultraThinMaterial,
                    in: Capsule()
                )
                .overlay {
                    Capsule()
                        .strokeBorder(.white.opacity(0.15))
                }
                .shadow(
                    color: .black.opacity(0.12),
                    radius: 12,
                    y: 4
                )
        }
    }
}

extension View {
    func glassCapsule() -> some View {
        modifier(GlassCapsuleModifier())
    }
}

 
