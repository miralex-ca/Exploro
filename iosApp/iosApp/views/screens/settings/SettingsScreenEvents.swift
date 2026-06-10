import SwiftUI
import Shared


class SettingsEventHandler {
    private let events: Events

    init(events: Events) {
        self.events = events
    }
 
    func onSettingAction(_ action: SettingAction) {
        if let action = action as? SettingAction.SetFavoriteSwipe {
            events.setFavoriteSwipeEnabled(enabled: action.enabled)
        } else if let action = action as? SettingAction.SetThemeMode {
            events.saveThemeMode(name: action.value)
        } else if action is SettingAction.SyncData {
            events.syncDataFromSettings()
        }
    }
}
