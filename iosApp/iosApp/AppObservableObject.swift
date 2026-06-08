

import SwiftUI
import Shared

class AppObservableObject: ObservableObject {
    let model : DKMPViewModel = DKMPViewModel.Factory().getIosInstance()
    var dkmpNav : Navigation {
        return self.model.navigation
    }
    @Published var localNavigationState : NavigationState
    @Published var screenStates = [ScreenIdentifier: any ScreenState]()
    @Published var appEnvironment: AppEnvironment
    
    init() {
        self.localNavigationState = model.navigation.navigationState
        self.appEnvironment = model.navigation.stateProvider.getAppEnvironmentFlow().value
    }

    @MainActor
    func collectScreenStateFlow(sID: ScreenIdentifier) async {
        for await state in model.navigation.stateProvider.getScreenStateFlow(screenIdentifier: sID) {
            self.screenStates[sID] = state
        }
    }
    
    @MainActor
        func collectAppEnvironment() async {
            for await env in model.navigation.stateProvider.getAppEnvironmentFlow() {
                self.appEnvironment = env
            }
        }
    
    func getScreenState(sID: ScreenIdentifier) -> ScreenState {
        return screenStates[sID] ?? model.navigation.stateProvider.getScreenStateFlow(screenIdentifier: sID).value
    }
}

