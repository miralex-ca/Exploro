
import SwiftUI
import Shared

@MainActor
final class SettingsViewModel: ObservableObject {
    @Published var settingsList: [SettingsCategory] = []

    private var task: Task<Void, Never>? = nil
    
    private var handler: SettingsSheetEventHandler?
    
    private var blacklist: [String] = []

    func bind(_ model: AppObservableObject, isTablet: Bool = false) {
        handler = SettingsSheetEventHandler(events: model.dkmpNav.events)
        
        task?.cancel()
        
        model.dkmpNav.events.updateSettingsState()
        
        if isTablet {
            blacklist = SettingsConfig.shared.tabletBlacklistedSettings
        }

        task = Task {
            await collectSettings(model)
        }
    }
    
    func send(_ action: SettingsUiAction) {
        handler?.onSettingAction(action)
    }

    private func collectSettings(_ model: AppObservableObject) async {
        for await value in model.dkmpNav
            .stateProvider
            .getSettingsFlow() {
            
            let settings = filterSettings(settings: value, blacklistedIds: blacklist)

            self.settingsList = settings
        }
    }

    deinit {
        task?.cancel()
    }
}
