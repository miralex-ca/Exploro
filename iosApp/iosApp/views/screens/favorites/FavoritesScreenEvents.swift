
import SwiftUI
import Shared


enum FavoritesUiEvent {
    case onItemClicked(FavoriteListItem)
    case removeFavorite(String)
}

struct FavoritesEventHandler {
    let events: Events
    let navActions: NavigationActions
    
    func onEvent(_ event: FavoritesUiEvent) {
        switch event {
        case .onItemClicked(let item):
            navActions.toDetailFromList(item.toDetailsNavParams())
        case .removeFavorite(let id):
            events.removeFromFavorites(code: id)
        }
    }
}

extension FavoriteListItem {
    func toDetailsNavParams() -> DetailsNavParams {
        DetailsNavParams(id: id, name: name)
    }
}
