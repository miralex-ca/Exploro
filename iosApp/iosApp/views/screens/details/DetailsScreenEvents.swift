
import SwiftUI
import ComposeApp

enum DetailsUiEvent {
    case toggleFavorite(String)
}

struct DetailsEventHandler {
    let events: Events
    
    func onEvent(_ event: DetailsUiEvent) {
        switch event {
        case .toggleFavorite(let id):
            events.toggleFavorite(code: id)
        }
    }
}

