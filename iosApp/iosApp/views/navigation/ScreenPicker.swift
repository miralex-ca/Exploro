import SwiftUI
import Shared

struct ScreenPicker: View {
    let requestedSId: ScreenIdentifier
    let level1ScreenIdentifier: ScreenIdentifier
    
    @EnvironmentObject var appObj: AppObservableObject
    @Environment(\.appTheme) var theme
    @Environment(\.appLayout) var appLayout
    
    @StateObject private var eventHandlers: EventHandlers
    
    init(requestedSId: ScreenIdentifier, level1ScreenIdentifier: ScreenIdentifier, eventHandlers: EventHandlers) {
        self.requestedSId = requestedSId
        self.level1ScreenIdentifier = level1ScreenIdentifier
        _eventHandlers = StateObject(wrappedValue: eventHandlers)
    }
    
    var body: some View {
        let state = appObj.getScreenState(sID: requestedSId)
        let navigation = appObj.dkmpNav
        let isLevel1 = requestedSId.screen.navigationLevel == 1
        
        ZStack {
            Color.clear.ignoresSafeArea()
            
            switch requestedSId.screen {
                
            case .homeScreen:
                HomeScreen(
                    screenState: state as! HomeScreenState,
                    eventHandler: eventHandlers.home
                )
                
            case .sectionScreen:
                SectionScreen(
                    screenState: state as! SectionScreenState,
                    eventHandler: eventHandlers.section
                )
                
                
            case .countryDetail:
                DetailsScreen(
                    screenState: state as! DetailsScreenState,
                    eventHandler: eventHandlers.details
                )
                
            case .favoritesScreen:
                FavoritesScreen(
                    screenState: state as! FavoritesScreenState,
                    eventHandler: eventHandlers.favorites
                )
                
            case .searchScreen:
                SearchScreen(
                    screenState: state as! SearchScreenState,
                    eventHandler: eventHandlers.search
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
        .navigationTitle(navigation.getTitle(screenIdentifier: requestedSId))
        .navigationBarTitleDisplayMode(.inline)
        .onDisappear {
            navigation.exitScreenForIos(screenIdentifier: requestedSId)
        }
        .task {
            await appObj.collectScreenStateFlow(sID: requestedSId)
        }
        .toolbar {
            if isLevel1 && !appLayout.useDrawer {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button {
                        eventHandlers.navActions.showSettings()
                    } label: {
                        Image(systemName: "gearshape")
                    }
                }
            }
        }
    }
}





