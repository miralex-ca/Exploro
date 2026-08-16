
import SwiftUI
import ComposeApp

 
struct NavigationActions {
    private let navigate: (Screen, (any ScreenParams)?) -> Void
    private let navigateByLevel1: (Level1Navigation) -> Void
    private let openSettings: () -> Void
    
    init(
        navigate: @escaping (Screen, (any ScreenParams)?) -> Void,
        navigateByLevel1: @escaping (Level1Navigation) -> Void,
        openSettings: @escaping () -> Void
    ) {
        self.navigate = navigate
        self.navigateByLevel1 = navigateByLevel1
        self.openSettings = openSettings
    }
    
    func toSearch() {
        navigate(.searchScreen, nil)
    }
    
    func showSettings() {
        openSettings()
    }
    
    func toLevel1Screen(_ level1: Level1Navigation) {
        navigateByLevel1(level1)
    }
    
    func toDetailFromList(_ params: DetailsNavParams) {
        navigate(.countryDetail, DetailsScreenParams(
            countryCode: params.id,
            screenTitle: params.name
        ))
    }
    func toSection(_ params: SectionNavParams) {
        navigate(.sectionScreen, SectionParams(
            continent: params.id,
            screenTitle: params.name
        ))
    }
    
    func navigateByLevel1(_ level1: Level1Navigation) {
        navigateByLevel1(level1)
    }
}

func makeNavActions(
    _ navigate: @escaping (Screen, (any ScreenParams)?) -> Void,
    _ navigateByLevel1: @escaping (Level1Navigation) -> Void,
    _ openSettings: @escaping () -> Void
)  -> NavigationActions {
    NavigationActions(
        navigate: navigate,
        navigateByLevel1: navigateByLevel1,
        openSettings: openSettings
    )
}

struct DetailsNavParams {
    let id: String
    let name: String
}

struct SectionNavParams{
    let id: String
    let name: String
}

