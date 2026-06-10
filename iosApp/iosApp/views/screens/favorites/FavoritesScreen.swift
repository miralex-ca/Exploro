
import SwiftUI
import Shared



struct FavoritesScreen: View {
    let screenState: FavoritesScreenState
    let eventHandler: FavoritesEventHandler

    var body: some View {
        ZStack {
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
                Text("Favorites")
                    .font(.title2)
                    .fontWeight(.semibold)
            }
        }
        .navigationBarTitleDisplayMode(.inline)
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









 

 

