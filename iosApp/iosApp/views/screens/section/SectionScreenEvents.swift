
import SwiftUI
import Shared


enum SectionUiEvent {
    case onItemClicked(SectionListItem)
}

struct SectionEventHandler {
    let navActions: ScreenNavActions
    
    func onEvent(_ event: SectionUiEvent) {
        switch event {
        case .onItemClicked(let item):
            navActions.toDetailFromList(item.toDetailsNavParams())
        }
    }
}

extension SectionListItem {
    func toDetailsNavParams() -> DetailsNavParams {
        DetailsNavParams(id: id, name: name)
    }
}
