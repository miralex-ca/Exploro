import SwiftUI
import Shared


struct ScreenPicker: View {
    @EnvironmentObject var appObj: AppObservableObject
    let requestedSId: ScreenIdentifier
    let level1ScreenIdentifier: ScreenIdentifier

    var body: some View {
        let state = appObj.getScreenState(sID: requestedSId)
        let nav = appObj.dkmpNav
        
        let isLevel1 = requestedSId.screen.navigationLevel == 1

        VStack {
            switch requestedSId.screen {

            case .homeScreen:
                HomeScreen(
                    screenState: state as! HomeScreenState,
                    onItemClick: { name in
                        navigate(.countryDetail, DetailsScreenParams(countryCode: name.id, screenTitle: name.name))
                    },
                    onSectionClick: { section in
//                        let sId = nav.navigate(.sectionScreen, SectionParams(continent: section.sectionId, screenTitle: section.sectionName))
//                        $appObj.localNavigationState.paths
//                            .getPath(level1URI: level1ScreenIdentifier.URI)
//                            .wrappedValue.append(sId)
//                        
                        navigate(.sectionScreen, SectionParams(continent: section.sectionId, screenTitle: section.sectionName))
                    }
                )

            case .countryDetail:
                DetailsScreen(
                    screenState: state as! DetailsScreenState,
                    onFavoriteClick: { id in appObj.dkmpNav.events.toggleFavorite(code: id)   }
                )

            case .sectionScreen:
                SectionScreen(
                    screenState: state as! SectionScreenState,
                    onItemClick: {name in
                        navigate(.countryDetail, DetailsScreenParams(countryCode: name.id, screenTitle: name.name))
                    }
                )
                
            case .settingsScreen:
                SettingsScreen(
                    screenState: state as! SettingsScreenState,
                    eventHandler: .init(events: appObj.dkmpNav.events)
                )
                
            case .favoritesScreen:
                FavoritesScreen(
                    screenState: state as! FavoritesScreenState,
                    onItemClick: {name in
                        navigate(.countryDetail, DetailsScreenParams(countryCode: name.id, screenTitle: name.name))
                    }
                )

            default: EmptyView()
            }
        }
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
                        navigate(.settingsScreen, nil)
                    } label: {
                        Image(systemName: "gearshape")
                    }
                }
            }
        }
         
   
    }
    
    func navigate(_ screen: Screen, _ params: ScreenParams?) {
        let sId = appObj.dkmpNav.navigate(screen, params)
        
        print("navigate to \(screen)")
        $appObj.localNavigationState.paths
            .getPath(level1URI: level1ScreenIdentifier.URI)
            .wrappedValue.append(sId)
    }
}



extension Navigation {
    
    @ViewBuilder func screenPicker(requestedSId: ScreenIdentifier, appObj: AppObservableObject) -> some View {
        
        VStack {
            
            let state = appObj.getScreenState(sID: requestedSId)
            
            switch requestedSId.screen {
                
            case .homeScreen:
                HomeScreen(
                    screenState: state as! HomeScreenState,
                    onItemClick: { name in
                        
                        print("item: \(name.name)")
                        print("calling navigate on: \(self)")
                        
                    //   self.navigate(.countryDetail, DetailsScreenParams(countryCode: name.id, screenTitle: name.name))
                        print("navigate called")
                        
                        let sId = self.navigate(.countryDetail, DetailsScreenParams(countryCode: name.id, screenTitle: name.name))
                        
                       // $appObj.localNavigationState.paths.getPath(level1URI: "level1ScreenIdentifier.URI") //.wrappedValue.append(sId)
                        
                        
                    },
                    onSectionClick: { section in
                        print("section: \(section.sectionName)")
                        self.navigate(.sectionScreen, SectionParams(continent: section.sectionId, screenTitle: section.sectionName))
                    }
                )
                
            case .countryDetail:
                DetailsScreen(
                    screenState: state as! DetailsScreenState,
                    onFavoriteClick: {_ in }
                )
            case .sectionScreen:
                SectionScreen(
                    screenState: state as! SectionScreenState,
                    onItemClick: {_ in }
                )
                
            case .favoritesScreen:
                FavoritesScreen(
                    screenState: state as! FavoritesScreenState,
                    onItemClick: {_ in
                        
                    }
                )
                
            default: EmptyView()
                
            }
            
            
            
        }
        
        .navigationTitle(getTitle(screenIdentifier: requestedSId))
        .navigationBarTitleDisplayMode(.inline)
        .onAppear {
            if requestedSId.URI == self.navigationState.topScreenIdentifier.URI {
                NSLog("iOS side:  onAppear URI "+requestedSId.URI)
            }
        }
        .onDisappear {
            self.exitScreenForIos(screenIdentifier: requestedSId)
        }
        .task {
            await appObj.collectScreenStateFlow(sID: requestedSId)
        }
        
    }
    
    
    
    
}
