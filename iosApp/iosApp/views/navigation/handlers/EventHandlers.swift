
import SwiftUI
import ComposeApp


class EventHandlers: ObservableObject {
    private let events: Events
    let navActions: NavigationActions
    
    init(events: Events, navActions: NavigationActions) {
        self.events = events
        self.navActions = navActions
    }
    
    lazy var home: HomeEventHandler = .init(navActions: navActions)
    lazy var section: SectionEventHandler = .init(navActions: navActions)
    lazy var details: DetailsEventHandler = .init(events: events)
    lazy var favorites: FavoritesEventHandler = .init(events: events, navActions: navActions)
    lazy var search: SearchEventHandler = .init(events: events, navActions: navActions)
}

