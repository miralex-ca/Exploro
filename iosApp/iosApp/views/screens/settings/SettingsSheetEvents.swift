import SwiftUI
import ComposeApp

enum SettingsUiAction {
    case toggle(key: String, value: Bool)
    case select(key: String, value: String)
    case action(key: String)
}


class SettingsSheetEventHandler {
    private let events: Events

    init(events: Events) {
        self.events = events
    }
    
    func onSettingAction(_ action: SettingsUiAction) {

        switch action {

        case let .toggle(key, value):
            events.updateSetting(key: key, value: value)

        case let .select(key, value):
            events.updateSetting(key: key, value: value)

        case let .action(key):
            events.triggerSettingAction(key: key)
        }
    }
}


