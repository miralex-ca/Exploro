import SwiftUI
import ComposeApp


enum SearchUiEvent {
    case OnItemClicked(item: SearchListItem)
    case SearchByQuery(query: String)
}

struct SearchEventHandler {
    let events: Events
    let navActions: NavigationActions
    
    func onEvent(_ event: SearchUiEvent) {
        switch event {
        case .OnItemClicked(let item):
            navActions.toDetailFromList(item.toDetailsNavParams())
        case .SearchByQuery(let query):
            events.searchCountriesByQuery(query: query)
        }
    }
}

extension SearchListItem {
    func toDetailsNavParams() -> DetailsNavParams {
        DetailsNavParams(id: id, name: name)
    }
}
