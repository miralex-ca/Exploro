

import SwiftUI
import Shared

struct AppNavController {
    let navigate: (Screen, (any ScreenParams)?) -> Void
    let navigateByLevel1: (Level1Navigation) -> Void
}

struct ScreenNavActions {
    private let navController: AppNavController
    
    init(navController: AppNavController) {
        self.navController = navController
    }
    
    func toSearch() {
        navController.navigate(.searchScreen, nil)
    }
    func toSettings() {
        navController.navigate(.settingsScreen, nil)
        //navController.navigateByLevel1(.lv1Settings)
    }
    func toLevel1Screen(_ level1: Level1Navigation) {
        navController.navigateByLevel1(level1)
    }
    func toDetailFromList(_ params: DetailsNavParams) {
        navController.navigate(.countryDetail, DetailsScreenParams(
            countryCode: params.id,
            screenTitle: params.name
        ))
    }
    func toSection(_ params: SectionNavParams) {
        navController.navigate(.sectionScreen, SectionParams(
            continent: params.id,
            screenTitle: params.name
        ))
    }
}

func makeNavActions(
    _ navigate: @escaping (Screen, (any ScreenParams)?) -> Void,
    _ navigateByLevel1: @escaping (Level1Navigation) -> Void
) -> ScreenNavActions {
    let navController = AppNavController(
        navigate: navigate,
        navigateByLevel1: navigateByLevel1
    )
    return ScreenNavActions(navController: navController)
}

struct DetailsNavParams {
    let id: String
    let name: String
}

struct SectionNavParams{
    let id: String
    let name: String
}

