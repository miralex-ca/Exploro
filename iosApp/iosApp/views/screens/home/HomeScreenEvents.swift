
import SwiftUI
import Shared


enum HomeUiEvent {
    case onItemClicked(HomeListItem)
    case onSectionClicked(HomeSectionState)
}

struct HomeEventHandler {
    let navActions: NavigationActions
    
    func onEvent(_ event: HomeUiEvent) {
        switch event {
        case .onItemClicked(let item):
            navActions.toDetailFromList(item.toDetailsNavParams())
        case .onSectionClicked(let section):
            navActions.toSection(section.toSectionNavParams())
        }
    }
}

extension HomeListItem {
    func toDetailsNavParams() -> DetailsNavParams {
        DetailsNavParams(id: id, name: name)
    }
}

extension HomeSectionState {
    func toSectionNavParams() -> SectionNavParams {
        SectionNavParams(id: sectionId, name: sectionName)
    }
}
