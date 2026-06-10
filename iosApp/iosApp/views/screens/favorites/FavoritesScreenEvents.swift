
import SwiftUI
import Shared


enum FavoritesUiEvent {
    case onItemClicked(FavoriteListItem)
    case removeFavorite(String)
    case selectCountry(FavoriteListItem)
    case updateFavoriteDetails(String)
    
}

struct FavoritesEventHandler {
    let events: Events
    let navActions: ScreenNavActions
    
    func onEvent(_ event: FavoritesUiEvent) {
        switch event {
        case .onItemClicked(let item):
            navActions.toDetailFromList(item.toDetailsNavParams())
        case .removeFavorite(let id):
            events.removeFavoriteBySwipe(code: id)
        case .selectCountry(let item):
            events.selectFavoriteCountry(itemId: item.id)
        case .updateFavoriteDetails(let itemId):
            events.updateDetailsInFavorites(code: itemId)
        }
    }
}

extension FavoriteListItem {
    func toDetailsNavParams() -> DetailsNavParams {
        DetailsNavParams(id: id, name: name)
    }
}
