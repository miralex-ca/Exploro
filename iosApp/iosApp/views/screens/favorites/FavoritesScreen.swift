
import SwiftUI
import Shared



struct FavoritesScreen: View {
    let screenState: FavoritesScreenState
    let eventHandler: FavoritesEventHandler

    var body: some View {
        
        let isIpad = UIDevice.current.userInterfaceIdiom == .pad
        Level1ScreenContainer (screenTitle: Strings.favoritesTitle) {
            ZStack {
                Color.clear
                if screenState.isLoading {
                    ScreenLoadingView()
                } else {
                    FavoritesScreenContent(
                        screenState: screenState,
                        onEvent: eventHandler.onEvent
                    )
                    
                }
            }
            
            .toolbar {
                ToolbarItem(placement: .principal) {
                    Text(isIpad ? "" : Strings.favoritesTitle)
                        .font(.title2)
                        .fontWeight(.semibold)
                }
            }
            .navigationBarTitleDisplayMode(.inline)
        }
        
    }
}


struct FavoritesScreenContent: View {
    let screenState: FavoritesScreenState
    let onEvent: (FavoritesUiEvent) -> Void

    var useGrid: Bool { UIDevice.current.userInterfaceIdiom == .pad }

    var body: some View {
        if screenState.favorites.isEmpty {
            EmptyView()
        } else if useGrid {
            FavoritesGrid(
                items: screenState.favorites,
                onEvent: onEvent
            )
        } else {
            FavoritesList(
                items: screenState.favorites,
                onEvent: onEvent
            )
        }
    }
}









 

 

