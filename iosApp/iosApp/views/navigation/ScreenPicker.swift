import SwiftUI
import Shared


struct ScreenPicker: View {
    let requestedSId: ScreenIdentifier
    let level1ScreenIdentifier: ScreenIdentifier
    let screenNavActions: ScreenNavActions
    
    @EnvironmentObject var appObj: AppObservableObject
    @Environment(\.appTheme) var theme
    

    var body: some View {
        let state = appObj.getScreenState(sID: requestedSId)
        let nav = appObj.dkmpNav
 
        let isLevel1 = requestedSId.screen.navigationLevel == 1

        ZStack {
            Color.clear.ignoresSafeArea()
            
            switch requestedSId.screen {

            case .homeScreen:
                
                HomeScreen(
                    screenState: state as! HomeScreenState,
                    eventHandler: HomeEventHandler(navActions: screenNavActions)
                )
                
            case .sectionScreen:
                SectionScreen(
                    screenState: state as! SectionScreenState,
                    eventHandler: SectionEventHandler(navActions: screenNavActions)
                )

            case .countryDetail:
                DetailsScreen(
                    screenState: state as! DetailsScreenState,
                    eventHandler: DetailsEventHandler(events: appObj.dkmpNav.events)
                )
                
            case .settingsScreen:
                SettingsScreen(
                    screenState: state as! SettingsScreenState,
                    eventHandler: .init(events: appObj.dkmpNav.events)
                )
                
            case .lv1SettingsScreen:
                SettingsScreen(
                    screenState: state as! SettingsScreenState,
                    eventHandler: .init(events: appObj.dkmpNav.events)
                )
                
            case .favoritesScreen:
                FavoritesScreen(
                    screenState: state as! FavoritesScreenState,
                    eventHandler: FavoritesEventHandler(events: appObj.dkmpNav.events, navActions: screenNavActions)
                )
                
            case .searchScreen:
                SearchScreen(
                    screenState: state as! SearchScreenState,
                    eventHandler: SearchEventHandler(events: appObj.dkmpNav.events, navActions: screenNavActions)
                )

            default: EmptyView()
                
            }
        }
        .background(theme.background)
        .safeAreaInset(edge: .bottom) {
            if isLevel1 {
                Color.clear.frame(height: 80)
            }
        }
        .navigationTitle(nav.getTitle(screenIdentifier: requestedSId))
        .navigationBarTitleDisplayMode(.inline)
        .onDisappear {
            nav.exitScreenForIos(screenIdentifier: requestedSId)
        }
        .task {
            await appObj.collectScreenStateFlow(sID: requestedSId)
        }
        .toolbar {
            if isLevel1 {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button {
                       // screenNavActions.toSettings()
                        appObj.showSettings = true
                    } label: {
                        Image(systemName: "gearshape")
                    }
                }
            }
        }
         
   
    }
    
    func navigate(_ screen: Screen, _ params: ScreenParams?) {
        let sId = appObj.dkmpNav.navigate(screen, params)
        $appObj.localNavigationState.paths
            .getPath(level1URI: level1ScreenIdentifier.URI)
            .wrappedValue.append(sId)
    }
}





