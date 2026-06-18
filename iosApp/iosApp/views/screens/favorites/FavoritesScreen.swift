
import SwiftUI
import Shared

struct FavoritesScreen: View {
    let screenState: FavoritesScreenState
    let eventHandler: FavoritesEventHandler
    @Environment(\.appLayout) var appLayout

    var body: some View {
        Level1ScreenContainer (screenTitle: Strings.favoritesTitle) {
            ZStack {
                Color.clear
                if screenState.isLoading {
                    LoadingScreen()
                        .transition(.opacity)
                } else {
                    FavoritesScreenContent(
                        screenState: screenState,
                        onEvent: eventHandler.onEvent
                    )
                    .transition(.opacity)
                }
            }
            .animation(.easeInOut(duration: 0.15), value: screenState.isLoading)
            .toolbar {
                ToolbarItem(placement: .principal) {
                    Text(appLayout.isTablet ? "" : Strings.favoritesTitle)
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
    @Environment(\.appLayout) var appLayout

    var body: some View {
        ZStack {
            if screenState.favorites.isEmpty {
                EmptyStateView(state: .emptyList)
                    .transition(.opacity)
            } else if appLayout.isTablet {
                FavoritesGrid(
                    items: screenState.favorites,
                    onEvent: onEvent
                )
                .transition(.opacity)
            } else {
                FavoritesList(
                    items: screenState.favorites,
                    onEvent: onEvent
                )
                .transition(.opacity)
            }
        }
        .animation(.easeInOut(duration: 0.15), value: screenState.favorites.isEmpty)
        
    }
}









 

 

