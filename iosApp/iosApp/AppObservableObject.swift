

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
    @Published var showSettings = false
    
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


@MainActor
final class SettingsViewModel: ObservableObject {

    @Published var settingsList: [SettingsCategory] = []

    private var task: Task<Void, Never>? = nil
    
    private var handler: SettingsSheetEventHandler?


    func bind(_ model: AppObservableObject) {
        handler = SettingsSheetEventHandler(events: model.dkmpNav.events)
        
        task?.cancel()
        
        model.dkmpNav.events.updateSettingsState()

        task = Task {
            await collectSettings(model)
        }
    }
    
    func send(_ action: SettingsAction) {
        handler?.onSettingAction(action)
    }

    private func collectSettings(_ model: AppObservableObject) async {
        for await value in model.dkmpNav
            .stateProvider
            .getSettingsFlow() {

            self.settingsList = value
        }
    }

    deinit {
        task?.cancel()
    }
}

