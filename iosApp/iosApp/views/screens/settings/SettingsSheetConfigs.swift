
import SwiftUI
import ComposeApp


class SettingsConfig {
    static let shared = SettingsConfig()
    private init() {}
    
    let tabletBlacklistedSettings: [String] = [InterfaceSettingsCategory.companion.FAVORITE_SWIPE_SETTING_ID]
}


extension Setting {
    var isNavigable: Bool {
        switch self {
        case is Setting.Options, is Setting.Action:
            return true
        default:
            return false
        }
    }
}

