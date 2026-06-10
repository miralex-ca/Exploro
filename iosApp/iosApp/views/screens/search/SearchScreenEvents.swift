import SwiftUI
import Shared


enum SearchUiEvent {
    case OnItemClicked(item: SearchListItem)
    case SearchByQuery(query: String)
}

struct SearchEventHandler {
    let events: Events
    let navActions: ScreenNavActions
    
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

 


//
//extension SearchScreen {
//    func onEvent(_ event: SearchUiEvent) {
//        let navigation = appObj.dkmpNav
//        let events = navigation.events
//
//        switch event {
//        case .OnItemClicked(let item):
//            print("")
//            
//        case .SearchByQuery(let query):
//            events.searchCountriesByQuery(query: query)
//        }
//    }
//}
